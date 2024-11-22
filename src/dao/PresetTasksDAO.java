package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.PresetTasks;

public class PresetTasksDAO {
	public List<PresetTasks> get() {
		List<PresetTasks> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM preset_tasks";
			PreparedStatement stmt = cn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				PresetTasks presetTasks = rs2model(rs);
				list.add(presetTasks);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	public PresetTasks find(int id) {
    	PresetTasks presetTasks = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM preset_tasks WHERE id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				presetTasks = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return presetTasks;
    }
	
	/**
	 * テーブルの中から、指定されたプリセット名を持つレコードを返すメソッド
	 * @param preset_name プリセット名
	 * @return 発見したデータ。なければnull
	 */
	public PresetTasks findByPresetTaskName(String name) {
		PresetTasks presetTasks = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM preset_tasks WHERE name = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				presetTasks = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return presetTasks;
	}
	
	public PresetTasks findByPresetTask(int presetId) {
		PresetTasks presetTasks = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM preset_tasks WHERE preset_id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, presetId);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				presetTasks = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return presetTasks;
	}
	
	public PresetTasks createPresetTask(int presetId, int categoryId, String name, LocalTime taskTime, String memo, int outin) {
	    int ret = -1;
	    LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), taskTime);
	    // 重複確認
	 		if (findByPresetTaskName(name) != null) {
	 			System.out.println("該当プリセットタスク名は既に存在しています");
	 			return null;
	 		}
	 		
	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        String sql = "INSERT INTO preset_tasks (preset_id, category_id, name, task_time, memo, outin) VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement stmt = cn.prepareStatement(sql);
	        stmt.setInt(1, presetId);
	        stmt.setInt(2, categoryId);
	        stmt.setString(3, name);
	        stmt.setTimestamp(4, Timestamp.valueOf(dateTime));
	        stmt.setString(5, memo);
	        stmt.setInt(6, outin);
	        
	        ret = stmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    if (ret >= 0) {
	        return find(presetId);
	    }
	    return null;
	}
	
	private PresetTasks rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int presetId = rs.getInt("preset_id");
        int categoryId = rs.getInt("category_id");
        String name = rs.getString("name");
        Timestamp timestamp = rs.getTimestamp("task_time");
        LocalTime taskTime = timestamp.toLocalDateTime().toLocalTime();
        String memo = rs.getString("memo");
        int outin = rs.getInt("outin");
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime(); 

        return new PresetTasks(id, presetId, categoryId, name, taskTime, memo, outin, createdAt, updateDate);
    }
}
