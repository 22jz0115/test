package servlet;

import java.security.Security;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import logic.TaskNotificationScheduler;

@WebListener
public class TaskNotificationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("バックグラウンドジョブを開始します...");
     // BouncyCastle プロバイダーを初期化
        if (Security.getProvider("BC") == null) {
        	Security.addProvider(new BouncyCastleProvider());
        }
        TaskNotificationScheduler.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("バックグラウンドジョブを停止します...");
        TaskNotificationScheduler.stop();
    }
}
