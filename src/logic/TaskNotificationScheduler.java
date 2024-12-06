package logic;

import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonObject;

import dao.SubscriptionsDAO;
import dao.TasksDAO;
import model.Subscriptions;
import model.Tasks;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;

public class TaskNotificationScheduler {
    private static ScheduledExecutorService scheduler;
    private static final String VAPID_PUBLIC_KEY = "BP198Ghlpaac41UMKrRvYyNt56tt7JCcgusX1JSh1e3W-kApskF2BLqCNi0c2GBjPx5BflPezwvi0OwVsaGTclI=";
    private static final String VAPID_PRIVATE_KEY = "Kzh4c_SbGLXoybns7pCiRdvhx9b0m_H5AEyD8DD9Bvw=";
    private static final String VAPID_SUBJECT = "https://graduation03.mydns.jp/test/Login";

    public static void start() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            // タスクの実行内容
            System.out.println("チェック中...");
            // 現在の時刻を取得
            Calendar now = Calendar.getInstance();
            int currentYear = now.get(Calendar.YEAR);
            int currentMonth = now.get(Calendar.MONTH + 1);
            int currentHour = now.get(Calendar.HOUR_OF_DAY);
            int currentMinute = now.get(Calendar.MINUTE);
            
            int currentDateTime = currentYear + currentMonth + currentHour + currentMinute;
            String current = currentDateTime + "";

            LocalDateTime dateTime = LocalDateTime.parse(current, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
            
            TasksDAO dao = new TasksDAO();
            List<Tasks> list = dao.findTaskTime(dateTime);
            
            if(list != null) {
            	//listが入っていたらfor文で実行
            	for(Tasks task : list) {
            		int accountId = task.getAccountId();
            		SubscriptionsDAO subDao = new SubscriptionsDAO(); 
            		Subscriptions userSubsc = subDao.findByAccount(accountId);
            		pushNotifiction(task, userSubsc);
            	}
            }
        }, 0, 1, TimeUnit.MINUTES); // 毎分チェック
    }
    
    public static void pushNotifiction(Tasks task, Subscriptions userSubsc) {
    	try {
            // 1. 通知データを作成
            JsonObject payload = new JsonObject();
            payload.addProperty("title", task.getTaskName());
            payload.addProperty("body", task.getMemo());

            // 2. Web Push通知オブジェクトを生成
            Notification notification = new Notification(
                userSubsc.getEnd_point(),
                userSubsc.getP256dh(),
                userSubsc.getAuth(),
                payload.toString()
            );

            // 3. PushServiceのセットアップ
            PushService pushService = new PushService();
            pushService.setPublicKey(Utils.loadPublicKey(VAPID_PUBLIC_KEY));
            pushService.setPrivateKey(Utils.loadPrivateKey(VAPID_PRIVATE_KEY));
            pushService.setSubject(VAPID_SUBJECT);
            
            // 4. 通知を送信
            pushService.send(notification);
            System.out.println("通知を送信しました: " + task.getTaskName());
        } catch (GeneralSecurityException e) {
            System.err.println("セキュリティエラー: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("通知送信中にエラーが発生しました: " + e.getMessage());
        }
    }

    public static void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}
