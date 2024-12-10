package logic;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.ECPublicKey;
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
            // VAPID 鍵ペアを準備
    		String publicKey = "LS0tLS1CRUdJTiBQVUJMSUMgS0VZLS0tLS0KTUZrd0V3WUhLb1pJemowQ0FRWUlLb1pJemowREFRY0RRZ0FFSDgrT3RSa3p1V1VTajE3cVZadzc1RC9OSmtsNgorUE1EUnFXZnJXaUZVMGVCNDEyTE5lR05mcUowTGxkNkswdnc5QjZlS3kvZk5nR05DakM5UUQzRmxnPT0KLS0tLS1FTkQgUFVCTElDIEtFWS0tLS0tCg==";
    		String privateKey = "LS0tLS1CRUdJTiBQUklWQVRFIEtFWS0tLS0tCk1JR0hBZ0VBTUJNR0J5cUdTTTQ5QWdFR0NDcUdTTTQ5QXdFSEJHMHdhd0lCQVFRZ1p4RitUTmltUk1xRHM4dXAKMXVreXNQR1Nka09SMFNubEd0YURoM1ZXMGNTaFJBTkNBQVFmejQ2MUdUTzVaUktQWHVwVm5EdmtQODBtU1hyNAo4d05HcFordGFJVlRSNEhqWFlzMTRZMStvblF1VjNvclMvRDBIcDRyTDk4MkFZMEtNTDFBUGNXVwotLS0tLUVORCBQUklWQVRFIEtFWS0tLS0tCg==";
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
        // 公開鍵と認証キーをBase64デコード
        byte[] p256dhBytes = Base64.getDecoder().decode(p256dh);
        byte[] authBytes = Base64.getDecoder().decode(auth);
        
        // ECPublicKeyを作成
        X509EncodedKeySpec spec = new X509EncodedKeySpec(p256dhBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        ECPublicKey publicKey = (ECPublicKey) keyFactory.generatePublic(spec);

        // 暗号化のための準備
        Cipher cipher = Cipher.getInstance("ECIES");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // ペイロードの暗号化
        byte[] encryptedPayload = cipher.doFinal(payload.getBytes("UTF-8"));

        // 認証キーとペイロードを結合して最終的なバイナリデータを作成
        byte[] result = new byte[authBytes.length + encryptedPayload.length];
        System.arraycopy(authBytes, 0, result, 0, authBytes.length);
        System.arraycopy(encryptedPayload, 0, result, authBytes.length, encryptedPayload.length);

        return result;
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
        // URLセーフBase64デコード
        byte[] decodedKey = Base64.getDecoder().decode(privateKeyBase64);

        // PKCS#8形式で秘密鍵を読み取る
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // ECDSAによる署名を生成
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);
        signature.update(unsignedToken);

        return signature.sign();
    }


    public static void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}