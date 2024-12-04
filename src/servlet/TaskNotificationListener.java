package servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import logic.TaskNotificationScheduler;

@WebListener
public class TaskNotificationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("バックグラウンドジョブを開始します...");
        TaskNotificationScheduler.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("バックグラウンドジョブを停止します...");
        TaskNotificationScheduler.stop();
    }
}
