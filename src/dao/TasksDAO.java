package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Tasks;


public class TasksDAO {
	public List<Tasks> get() {
		List<Tasks> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM tasks";
			PreparedStatement stmt = cn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				Tasks tasks = rs2model(rs);
				list.add(tasks);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	public Tasks find(int id) {
    	Tasks tasks = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM tasks WHERE id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				tasks = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return tasks;
    }
	
	/**
	 * テーブルの中から、指定されたメールアドレスを持つレコードを返すメソッド
	 * @param email メールアドレス
	 * @return 発見したデータ。なければnull
	 */
	public Tasks findByTaskName(String task_name) {
		Tasks tasks = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM tasks WHERE task_name = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, task_name);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				tasks = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return tasks;
	}
	
	/**
	 * DBにデータを追加する
	 * @return 成功時は追加したデータ、失敗時はnull
	 */
	public Tasks create(int category_id, int account_id, String task_name, String memo, int outin) {
		int ret = -1;
		
		// 重複確認
		if (findByTaskName(task_name) != null) {
			System.out.println("該当タスクは既に存在しています");
			return null;
		}
		
		// DBにデータを追加
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			
			// プレースホルダで変数部分を定義
			String sql = "INSERT INTO tasks (category_id, account_id, task_name, memo, outin) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, category_id);
			stmt.setInt(2, account_id);
			stmt.setString(3, task_name);
			stmt.setString(4, memo);
			stmt.setInt(5, outin);
			
			ret = stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if (ret >= 0) {
			return findByTaskName(task_name);
		}
		return null;
	}
	
	private Tasks rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int category_id = rs.getInt("category_id");
        int account_id = rs.getInt("account_id");
        String task_name = rs.getString("task_name");
        LocalDateTime taskDatetime =
                rs.getTimestamp("task_datetime").toLocalDateTime(); 
        String memo = rs.getString("memo");
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime(); 
        int outin = rs.getInt("outin"); 

        return new Tasks(id, category_id, account_id, task_name, taskDatetime, memo, createdAt, updateDate, outin);
    }
}
