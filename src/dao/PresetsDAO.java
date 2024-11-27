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
	public List<Presets> get(int accountId) {
		List<Presets> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM presets WHERE account_id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, accountId);
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
	
	public List<String> getName(int accountId) {
		List<String> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT preset_name FROM presets WHERE account_id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, accountId);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				String presetName = rs.getString("preset_name");
				list.add(presetName);
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
	
	public int findByPresetId(String preset_name, int account_id) {
		int presetId = 0;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT id FROM presets WHERE preset_name = ? AND account_id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, preset_name);
			stmt.setInt(2, account_id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				presetId = rs.getInt("id");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return presetId;
	}
	
	public Presets findByPreset(String preset_name, int account_id) {
		Presets presets = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM presets WHERE preset_name = ? AND account_id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, preset_name);
			stmt.setInt(2, account_id);
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
	public Presets create(String preset_name, int account_id) {
		int ret = -1;
		
		// DBにデータを追加
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			
			// プレースホルダで変数部分を定義
			String sql = "INSERT INTO presets (preset_name, account_id) VALUES (?, ?)";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, preset_name);
			stmt.setInt(2, account_id);
			
			ret = stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if (ret >= 0) {
			return findByPreset(preset_name, account_id);
		}
		return null;
	}
	
	public boolean deleteAllPresetTask(int presetId) {
		int ret = -1;
	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        // SQL文をDELETE文に変更
	        String sql = "DELETE FROM preset_tasks WHERE preset_id = ?";
	        PreparedStatement stmt = cn.prepareStatement(sql);
	        // プレースホルダーに値を設定
	        stmt.setInt(1, presetId);
	        // 実行結果を取得
	        ret = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    // 更新された行が1行以上かどうかを返す
	    return ret > 0;
	}
	
	public boolean deleteAllPreset(int presetId) {
		int ret = -1;
		boolean result = deleteAllPresetTask(presetId);
		if(result == true) {
			DBManager manager = DBManager.getInstance();
		    try (Connection cn = manager.getConnection()) {
		        // SQL文をDELETE文に変更
		        String sql = "DELETE FROM presets WHERE id = ?";
		        PreparedStatement stmt = cn.prepareStatement(sql);
		        // プレースホルダーに値を設定
		        stmt.setInt(1, presetId);
		        // 実行結果を取得
		        ret = stmt.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	    // 更新された行が1行以上かどうかを返す
	    return ret > 0;
	}
	
	private Presets rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String preset_name = rs.getString("preset_name");
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime(); 
        int accountId = rs.getInt("account_id");

        return new Presets(id, preset_name, createdAt, updateDate, accountId);
    }
}
