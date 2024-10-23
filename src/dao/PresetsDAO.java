package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Presets;


public class PresetsDAO {
	public List<Presets> get() {
		List<Presets> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM presets";
			PreparedStatement stmt = cn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				Presets presets = rs2model(rs);
				list.add(presets);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	public Presets find(int id) {
    	Presets presets = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM presets WHERE id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				presets = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return presets;
    }
	
	/**
	 * テーブルの中から、指定されたプリセット名を持つレコードを返すメソッド
	 * @param preset_name プリセット名
	 * @return 発見したデータ。なければnull
	 */
	public Presets findByPresetName(String preset_name) {
		Presets presets = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM presets WHERE preset_name = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, preset_name);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				presets = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return presets;
	}
	
	/**
	 * DBにデータを追加する
	 * @return 成功時は追加したデータ、失敗時はnull
	 */
	public Presets create(int task_id, int category_id, int account_id, String preset_name) {
		int ret = -1;
		
		// 重複確認
		if (findByPresetName(preset_name) != null) {
			System.out.println("該当プリセット名は既に存在しています");
			return null;
		}
		
		// DBにデータを追加
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			
			// プレースホルダで変数部分を定義
			String sql = "INSERT INTO presets (task_id, category_id, account_id, preset_name) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, task_id);
			stmt.setInt(2, category_id);
			stmt.setInt(3, account_id);
			stmt.setString(4, preset_name);
			
			ret = stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if (ret >= 0) {
			return findByPresetName(preset_name);
		}
		return null;
	}
	
	private Presets rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int task_id = rs.getInt("task_id");
        int category_id = rs.getInt("category_id");
        int account_id = rs.getInt("account_id");
        String preset_name = rs.getString("preset_name");
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime();  

        return new Presets(id, task_id, category_id, account_id, preset_name, createdAt, updateDate);
    }
}
