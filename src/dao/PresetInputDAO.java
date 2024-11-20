package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.PresetTask;
import model.Presets;

public class PresetInputDAO {
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
	
	public int findByPresetId(String preset_name, int account_id) {
		int resultId = 0;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT id FROM presets WHERE preset_name = ? AND account_id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, preset_name);
			stmt.setInt(2, account_id);
			ResultSet rs = stmt.executeQuery();
			
			// 結果が存在する場合のみ処理
	        if (rs.next()) {
	            resultId = rs.getInt("id"); // カラム名 "id" を指定
	        } else {
	            System.out.println("結果が見つかりませんでした");
	        }
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return resultId;
	}
	
	/**
	 * テーブルの中から、指定されたプリセット名を持つレコードを返すメソッド
	 * @param preset_name プリセット名
	 * @return 発見したデータ。なければnull
	 */
	public Presets findByPresetName(String preset_name, int account_id) {
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
	
	public Presets createPreset(String presetName, Map<String, Object> taskData, int accountId) {
	    int presetId = -1;

	    // 初期タスクをJSON形式で作成
	    JSONObject initialTask = new JSONObject(taskData);
	    JSONArray tasksArray = new JSONArray();
	    tasksArray.put(initialTask);

	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        String sql = "INSERT INTO presets (preset_name, tasks, account_id) VALUES (?, ?, ?)";
	        PreparedStatement stmt = cn.prepareStatement(sql);
	        stmt.setString(1, presetName);
	        stmt.setString(2, tasksArray.toString());
	        stmt.setInt(3, accountId);
	        stmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    if (presetId > 0) {
	        return find(presetId);
	    }
	    return null;
	}
	
	public boolean addTaskToPreset(int presetId, Map<String, Object> taskData) {
	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        // 1. 現在のタスクを取得
	        String fetchSql = "SELECT tasks FROM presets WHERE id = ?";
	        PreparedStatement fetchStmt = cn.prepareStatement(fetchSql);
	        fetchStmt.setInt(1, presetId);
	        ResultSet rs = fetchStmt.executeQuery();

	        JSONArray tasksArray = new JSONArray();
	        if (rs.next()) {
	            String tasksJson = rs.getString("tasks");
	            if (tasksJson == null || tasksJson.isEmpty()) {
	                tasksArray = new JSONArray(); // 空の配列を初期化
	            } else {
	                tasksArray = new JSONArray(tasksJson);
	            }
	        }
	        if (tasksArray.length() >= 10) {
	            System.out.println("タスクの上限に達しました。");
	            return false;
	        }


	        // 2. 新しいタスクを追加
	        JSONObject newTask = new JSONObject(taskData);
	        tasksArray.put(newTask);

	        // 3. 更新
	        String updateSql = "UPDATE presets SET tasks = ? WHERE id = ?";
	        PreparedStatement updateStmt = cn.prepareStatement(updateSql);
	        updateStmt.setString(1, tasksArray.toString());
	        updateStmt.setInt(2, presetId);
	        updateStmt.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	
	private Presets rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String preset_name = rs.getString("preset_name");
        
     // JSON形式の文字列を取得
        String tasksJsonString = rs.getString("tasks");
        
        // 取得したJSON文字列をJSONArrayに変換
        JSONArray tasksJson = new JSONArray(tasksJsonString);
        
     // JSON文字列がnullまたは空の場合のデフォルト処理
        if (tasksJsonString == null || tasksJsonString.isEmpty()) {
            tasksJsonString = "{}"; // 空のJSONオブジェクトを代入
        }

        try {
            tasksJson = new JSONArray(tasksJsonString); // JSONパース
        } catch (JSONException e) {
            System.err.println("Invalid JSON: " + tasksJsonString);
            tasksJson = new JSONArray(); // 空のJSONオブジェクト
        }

        // 必要に応じて、tasksJsonから個別のタスクを取り出すことができます
        List<PresetTask> tasks = new ArrayList<>();
        for (int i = 0; i < tasksJson.length(); i++) {
            JSONObject taskJson = tasksJson.getJSONObject(i);
            int outin = taskJson.getInt("outin");
            String name = taskJson.getString("name");
            String memo = taskJson.getString("memo");
            int categoryId = taskJson.getInt("category_id");
            String taskTime = taskJson.getString("task_time");

            // PresetTaskオブジェクトを作成してリストに追加
            tasks.add(new PresetTask(name, memo, outin, categoryId, taskTime));
        }
        
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime(); 
        int accountId = rs.getInt("account_id");

        return new Presets(id, preset_name, createdAt, updateDate, tasks, accountId);
    }
}
