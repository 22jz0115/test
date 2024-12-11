package logic;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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

import org.bouncycastle.jce.provider.BouncyCastleProvider;

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
            String publicKey = "BDkzLFhl0ZDx2ho-Snk_ZIITgZjBfXGunbNgycezdritqHIuESNIJkjmG_-LFM5ikVVlRZBmWk1m9uJzRRkZ19Y";
            String privateKey = "-----BEGIN EC PRIVATE KEY-----\r\n"
                    + "MC4CAQEEIC3s3+jbbB5FiBoyOQ5efKNFoTcjKIo2jnAytv8DqN8RoAcGBSuBBAAK\r\n"
                    + "-----END EC PRIVATE KEY-----";
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
        // 公開鍵と認証キーをデコード
        byte[] p256dhBytes = Base64.getUrlDecoder().decode(p256dh);
        byte[] authBytes = Base64.getUrlDecoder().decode(auth);

        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(p256dhBytes);
        PublicKey recipientPublicKey = keyFactory.generatePublic(keySpec);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(256);
        KeyPair ephemeralKeyPair = keyPairGenerator.generateKeyPair();

        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", "BC");
        keyAgreement.init(ephemeralKeyPair.getPrivate());
        keyAgreement.doPhase(recipientPublicKey, true);
        byte[] sharedSecret = keyAgreement.generateSecret();

        byte[] salt = authBytes;
        byte[] derivedKey = hkdf(sharedSecret, salt);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        SecretKey aesKey = new SecretKeySpec(derivedKey, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);

        return cipher.doFinal(payload.getBytes(StandardCharsets.UTF_8));
    }

    // HKDFの実装（簡易版）
    private static byte[] hkdf(byte[] inputKeyingMaterial, byte[] salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt);
        return digest.digest(inputKeyingMaterial);
    }

    // 例としてVAPIDトークンの生成
    public static String createVapidToken(String endpoint, String publicKey, String privateKey, String subject) throws Exception {
        // VAPIDのヘッダーとペイロードを作成
        String header = Base64.getUrlEncoder().withoutPadding().encodeToString("{\"alg\":\"ES256\"}".getBytes());
        String payload = Base64.getUrlEncoder().withoutPadding().encodeToString((
            "{\"aud\":\"" + endpoint + "\"," 
            + "\"exp\":" + (System.currentTimeMillis() / 1000 + 12 * 60 * 60) + ","
            + "\"sub\":\"" + subject + "\"}"
        ).getBytes());

        // 署名の生成
        String unsignedToken = header + "." + payload;
        byte[] signature = signToken(privateKey, unsignedToken.getBytes());

        // VAPIDトークンを作成
        return unsignedToken + "." + Base64.getUrlEncoder().withoutPadding().encodeToString(signature);
    }

    // JWT署名を生成（秘密鍵を使って）
    public static byte[] signToken(String privateKeyBase64, byte[] unsignedToken) throws Exception {
        try {
        	// BouncyCastleがまだ登録されていない場合、登録
            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            // 秘密鍵の整形とデコード
            String privateKeyCleaned = privateKeyBase64.replace("-----BEGIN EC PRIVATE KEY-----", "")
                    .replace("-----END EC PRIVATE KEY-----", "")
                    .replace("\r\n", "")
                    .replace("\n", "");
            byte[] decodedKey = Base64.getDecoder().decode(privateKeyCleaned);

         // KeyFactoryを使ってEC秘密鍵を生成
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
            ECPrivateKey privateKeyObj = (ECPrivateKey) keyFactory.generatePrivate(keySpec);

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


    public static void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}