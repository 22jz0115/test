package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import servlet.TaskInput;

public class TaskeInputDAO {
    // データベース接続情報
    private static final String URL = "jdbc:mysql://localhost:3306/yourDatabase";
    private static final String USER = "yourUsername";
    private static final String PASSWORD = "yourPassword";

    public TaskInput create(String date, String time, String category, String taskName, String memo) {
        String sql = "INSERT INTO tasks (date, time, category, task_name, memo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // プレースホルダーにデータをセット
            pstmt.setString(1, date);
            pstmt.setString(2, time);
            pstmt.setString(3, category);
            pstmt.setString(4, taskName);
            pstmt.setString(5, memo);

            // SQLを実行
            int rowsAffected = pstmt.executeUpdate();

            // データが挿入された場合、新しいTaskオブジェクトを返す
            if (rowsAffected > 0) {
                return new TaskInput();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 挿入が失敗した場合は null を返す
        return null;
    }
}
