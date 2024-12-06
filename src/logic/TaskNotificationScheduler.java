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
    		System.out.println("メソッドきてます");
            JsonObject payload = new JsonObject();
            try {
                // 1. JsonObjectの生成
                payload.addProperty("title", task.getTaskName());  // 例: task.getTaskName() の部分で例外が発生する可能性
                payload.addProperty("body", task.getMemo());      // 例: task.getMemo() の部分で例外が発生する可能性

                System.out.println("Payload created: " + payload.toString());

            } catch (Exception e) {
                System.out.println("Error creating payload: " + e.getMessage());
            }  

    		Notification notification = null;
    		try {
    			System.out.println("ここはok");
    		    // Notificationのインスタンス化
    		    notification = new Notification(
    		        userSubsc.getEnd_point(),  // userSubsc.getEnd_point() が null でないか確認
    		        userSubsc.getP256dh(),     // userSubsc.getP256dh() が null でないか確認
    		        userSubsc.getAuth(),       // userSubsc.getAuth() が null でないか確認
    		        payload.toString()         // payload.toString() が null でないか確認
    		    );
    		    System.out.println("Notification created successfully");

    		} catch (IllegalArgumentException e) {
    		    // 引数に問題があった場合
    		    System.out.println("Error in Notification creation: " + e.getMessage());
    		} catch (NullPointerException e) {
    		    // null 参照が原因の場合
    		    System.out.println("Null reference error in Notification creation: " + e.getMessage());
    		} catch (Exception e) {
    		    // その他の予期しないエラー
    		    System.out.println("Unexpected error while creating notification: " + e.getMessage());
    		}

            PushService pushService = new PushService();
            try {
                // 3. PushServiceのセットアップ
                pushService.setPublicKey(Utils.loadPublicKey(VAPID_PUBLIC_KEY));
                pushService.setPrivateKey(Utils.loadPrivateKey(VAPID_PRIVATE_KEY));
                pushService.setSubject(VAPID_SUBJECT);

                System.out.println("PushService setup completed");

            } catch (Exception e) {
                System.out.println("Error setting up PushService: " + e.getMessage());
            }
            
            if (notification != null) {
                try {
                    // 4. 通知を送信
                    pushService.send(notification);
                    System.out.println("Notification sent successfully");
                } catch (Exception e) {
                    System.out.println("Error sending notification: " + e.getMessage());
                }
            } else {
                System.out.println("Notification was not created, skipping sending.");
            }
            
        } catch (Exception e) {
            System.out.println("通知送信中にエラーが発生しました: " + e.getMessage());
        }
    }

    public static void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}
