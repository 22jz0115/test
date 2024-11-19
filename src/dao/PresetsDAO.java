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
	public Presets create(String preset_name, List<Map<String, Object>> tasks) {
		int ret = -1;
		
		// 重複確認
		if (findByPresetName(preset_name) != null) {
			System.out.println("該当プリセット名は既に存在しています");
			return null;
		}
		
		// JSON形式でタスクデータを作成
        JSONObject tasksJson = new JSONObject();
        
        // 各タスクの情報をループで追加
        for (int i = 0; i < tasks.size(); i++) {
            Map<String, Object> task = tasks.get(i);
            JSONObject taskJson = new JSONObject();
            taskJson.put("name", task.get("name"));
            taskJson.put("memo", task.get("memo"));
            taskJson.put("outin", task.get("outin"));
            taskJson.put("category_id", task.get("category_id"));
            taskJson.put("task_time", task.get("task_time"));
            
            // task1, task2, ... のようにキーを生成してJSONに追加
            tasksJson.put("task" + (i + 1), taskJson);
        }
        
        String tasksJsonString = tasksJson.toString();
		
		// DBにデータを追加
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			
			// プレースホルダで変数部分を定義
			String sql = "INSERT INTO presets (preset_name, tasks) VALUES (?, ?)";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, preset_name);
			stmt.setString(2, tasksJsonString);
			
			ret = stmt.executeUpdate();
			System.out.println("データが正常に挿入されました");
			
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
        String preset_name = rs.getString("preset_name");
        
     // JSON形式の文字列を取得
        String tasksJsonString = rs.getString("tasks");
        
        // 取得したJSON文字列をJSONObjectに変換
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
