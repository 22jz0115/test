package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskeInputDAO {
    // データベース接続情報
    private static final String URL = "jdbc:mysql://localhost:3306/yourDatabase";
    private static final String USER = "yourUsername";
    private static final String PASSWORD = "yourPassword";

    // データベースへのタスクデータの挿入メソッド
    public boolean insertTask(String date, String time, String category, String taskName, String memo) {
        String sql = "INSERT INTO tasks (date, time, category, task_name, memo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // プレースホルダーにデータをセット
            pstmt.setString(1, date);
            pstmt.setString(2, time);
            pstmt.setString(3, category);
            pstmt.setString(4, taskName);
            pstmt.setString(5, memo);

            // SQL実行（更新された行数が1以上なら成功とする）
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}