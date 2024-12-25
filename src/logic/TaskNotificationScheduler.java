package logic;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.json.JSONObject;

import dao.SubscriptionsDAO;
import dao.TasksDAO;
import model.Subscriptions;
import model.Tasks;

public class TaskNotificationScheduler {
    private static ScheduledExecutorService scheduler;
    
    public static void start() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            // 現在の時刻を取得
            Calendar now = Calendar.getInstance();
            int currentYear = now.get(Calendar.YEAR);
            int currentMonth = now.get(Calendar.MONTH) + 1;
            int currentDay = now.get(Calendar.DAY_OF_MONTH);
            int currentHour = now.get(Calendar.HOUR_OF_DAY);
            int currentMinute = now.get(Calendar.MINUTE);
            
         // 現在時刻をyyyyMMddHHmm形式に変換
            String currentDateTime = (currentYear + "/" + currentMonth + "/" + currentDay + " " + currentHour + ":" + currentMinute + ":00");
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:m:ss");
            LocalDateTime dateTime = LocalDateTime.parse(currentDateTime, formatter);
    		System.out.println("dateTime=" + dateTime);

            TasksDAO dao = new TasksDAO();
            List<Tasks> list = dao.findTaskTime(dateTime);
            
            if(list != null && !list.isEmpty()) {
            	//listが入っていたらfor文で実行
            	for(Tasks task : list) {
            		int accountId = task.getAccountId();
            		SubscriptionsDAO subDao = new SubscriptionsDAO(); 
            		Subscriptions userSubsc = subDao.findByAccount(accountId);
            		System.out.println("push送るよ");
            		System.out.println("endpoint" + userSubsc.getEnd_point());
            		System.out.println("p256" + userSubsc.getP256dh());
            		System.out.println("auth" + userSubsc.getAuth());
            		try {
						pushNotifiction(task, userSubsc);
					} catch (GeneralSecurityException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
            	}
            }
        }, 0, 1, TimeUnit.MINUTES); // 毎分チェック
    }
    
    public static void pushNotifiction(Tasks task, Subscriptions userSubsc) throws GeneralSecurityException {
        try {
            // Bouncy Castleが登録されていない場合に登録
            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            // VAPID 鍵ペアを準備
            String publicKey = "BBNgWYrBUGNBxLIb5IOUufjXNNkP-NWOwyt7k4QFxRxQfkZWKzBwsRwx_NnbNEyJLXeTOHnbXagsT-e_7wmkmMo";
            String privateKey = "-----BEGIN PRIVATE KEY-----\r\n"
            		+ "MD4CAQAwEAYHKoZIzj0CAQYFK4EEAAoEJzAlAgEBBCA3uA+5Qq2mkMkkF3c+qBrj\r\n"
            		+ "7+OjxuJoBHEaQ+QyYhCgmQ==\r\n"
            		+ "-----END PRIVATE KEY-----";
            String subject = "https://graduation03.mydns.jp/test/Login";

            // ペイロードの生成
            String payload = "{\"title\":\"" + task.getTaskName() + "\",\"body\":\"" + task.getMemo() + "\"}";
            System.out.println("Payload created: " + payload);

            // VAPID トークンの生成
            String vapidToken = createVapidToken(userSubsc.getEnd_point(), publicKey, privateKey, subject);

            // Web Push 通知を送信
            URL url = new URL(userSubsc.getEnd_point());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setRequestProperty("Authorization", "WebPush " + vapidToken);
            connection.setRequestProperty("TTL", "2419200"); // 有効期限 (秒)
            connection.setDoOutput(true);

            // 暗号化されたペイロードを送信
            byte[] encryptedPayload = encryptPayload(payload, userSubsc.getP256dh(), userSubsc.getAuth());
            try (OutputStream os = connection.getOutputStream()) {
                os.write(encryptedPayload);
            }

            // レスポンスコードの確認
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // p256dhとauthキーを使ってペイロードを暗号化
    public static byte[] encryptPayload(String payload, String p256dh, String auth) throws Exception {
    	// p256dhの公開鍵を生成
        PublicKey recipientPublicKey = generatePublicKey(p256dh);

     // エピメラル（一時的）鍵ペア生成
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(256);  // 256ビットの鍵生成
        KeyPair ephemeralKeyPair = keyPairGenerator.generateKeyPair();

        // ECDH（楕円曲線Diffie-Hellman）鍵共有の計算
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", "BC");
        keyAgreement.init(ephemeralKeyPair.getPrivate());
        keyAgreement.doPhase(recipientPublicKey, true);
        byte[] sharedSecret = keyAgreement.generateSecret();

        // HKDF（ハッシュ鍵導出関数）を使って暗号化キーを導出
        byte[] salt = Base64.getUrlDecoder().decode(auth); // authキーを使用
        byte[] derivedKey = hkdf(sharedSecret, salt);

        // AES-GCMで暗号化
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        SecretKey aesKey = new SecretKeySpec(derivedKey, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);

        // ペイロードの暗号化
        return cipher.doFinal(payload.getBytes());
    }
    
    // HKDFの実装（簡易版）
    private static byte[] hkdf(byte[] inputKeyingMaterial, byte[] salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt);
        return digest.digest(inputKeyingMaterial);
    }
    
    public static PublicKey generatePublicKey(String p256dh) throws GeneralSecurityException {
    	// p256dhをURLセーフ形式からデコード
        byte[] p256dhBytes = decodeBase64UrlSafe(p256dh);
        System.out.println("Decoded p256dh (hex): " + bytesToHex(p256dhBytes));

        // P-256曲線のパラメータを取得
        ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("P-256");
        ECCurve curve = ecSpec.getCurve();

        // ECPointをデコード (圧縮形式を解凍)
        ECPoint ecPoint = curve.decodePoint(p256dhBytes);

        // ECPointが無効な場合は例外をスロー
        if (!ecPoint.isValid()) {
            throw new IllegalArgumentException("Invalid ECPoint");
        }

        // 公開鍵スペックを作成
        ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(ecPoint, ecSpec);

        // Bouncy Castleプロバイダを追加
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        // KeyFactoryを利用して公開鍵を生成
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");

        // 公開鍵の生成と例外処理
        try {
            return keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e) {
            System.err.println("InvalidKeySpecException: " + e.getMessage());
            System.err.println("p256dhBytes: " + bytesToHex(p256dhBytes));
            System.err.println("ECPoint: " + ecPoint);
            System.err.println("ECSpec: " + ecSpec);
            throw e;
        }
    }
    
    public static byte[] decodeBase64UrlSafe(String base64) {
        // Base64をURLセーフ形式に変換（+ -> -, / -> _, = を削除）
        String base64UrlSafe = base64
                .replace('+', '-')
                .replace('/', '_')
                .replace("=", "");
        // URLセーフ形式でデコード
        return Base64.getUrlDecoder().decode(base64UrlSafe);
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    // 例としてVAPIDトークンの生成
    public static String createVapidToken(String endpoint, String publicKey, String privateKey, String subject) throws Exception {
    	JSONObject headerJson = new JSONObject();
        headerJson.put("alg", "ES256");

        JSONObject payloadJson = new JSONObject();
        payloadJson.put("aud", endpoint);
        payloadJson.put("exp", System.currentTimeMillis() / 1000 + 12 * 60 * 60); // 12時間
        payloadJson.put("sub", subject);

        String header = Base64.getUrlEncoder().withoutPadding().encodeToString(headerJson.toString().getBytes());
        String payload = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.toString().getBytes());

        String unsignedToken = header + "." + payload;
        byte[] signature = signToken(privateKey, unsignedToken.getBytes());

        return unsignedToken + "." + Base64.getUrlEncoder().withoutPadding().encodeToString(signature);
    }

    // JWT署名を生成（秘密鍵を使って）
    public static byte[] signToken(String privateKeyBase64, byte[] unsignedToken) throws Exception {
    	try {
            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            
            // 修正されたloadPrivateKeyメソッドを使用
            PrivateKey privateKeyObj = loadPrivateKey(privateKeyBase64);

            // ECDSAによる署名を生成
            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initSign(privateKeyObj);
            signature.update(unsignedToken);

            return signature.sign();
        } catch (Exception e) {
            System.err.println("署名の生成に失敗しました: " + e.getMessage());
            throw e;
        }
    }
    
 // PEM形式の秘密鍵をデコードしてPrivateKeyオブジェクトを生成
    private static PrivateKey loadPrivateKey(String privateKeyPem) throws Exception {
        String privateKeyCleaned = privateKeyPem
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s", ""); // 改行と空白を除去
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyCleaned);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
        return keyFactory.generatePrivate(keySpec);
    }

    public static void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}