package logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import dao.TasksDAO;
import model.Tasks;

public class TaskNotificationScheduler {
    private static ScheduledExecutorService scheduler;

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
            }
        }, 0, 1, TimeUnit.MINUTES); // 毎分チェック
    }

    public static void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}
