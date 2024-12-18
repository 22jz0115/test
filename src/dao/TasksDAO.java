package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.PresetTasks;
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
	
	public List<Tasks> findByTaskList(int account_id) {
		List<Tasks> taskList = new ArrayList<>();
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM tasks WHERE account_id = ?　ORDER　BY 5";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, account_id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while (rs.next()) {
				Tasks tasks = rs2model(rs);
				taskList.add(tasks);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return taskList;
	}
	
	public List<Tasks> findByCheckTask(int account_id, String date) {
	    List<Tasks> taskList = new ArrayList<>();
	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        // SQL文でtask_datetimeの年月日部分のみを比較
	        String sql = "SELECT * FROM tasks WHERE account_id = ? AND TRUNC(task_datetime) = TO_DATE(?, 'YYYY-MM-DD') ORDER BY 5";
	        
	        PreparedStatement stmt = cn.prepareStatement(sql);
	        stmt.setInt(1, account_id);
	        
	     
	        
	        // パラメータとしてdateをセット
	        stmt.setString(2, date);
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        // データをリストに格納
	        while (rs.next()) {
	            taskList.add(rs2model(rs));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return taskList;
	}



	
	public List<Tasks> findByCategoryId(int account_id, int category_id) {
		List<Tasks> taskList = new ArrayList<>();
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM tasks WHERE account_id = ? and category_id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, account_id);
			stmt.setInt(2, category_id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while (rs.next()) {
				taskList.add(rs2model(rs));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return taskList;
	}
	
	
	public boolean updateCheckTask(int taskId, int check) {
	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        // SQL文で指定されたタスクの状態を更新
	        String sql = "UPDATE tasks SET check_Task = ?, update_date = CURRENT_TIMESTAMP WHERE id = ?";
	        PreparedStatement stmt = cn.prepareStatement(sql);
	        stmt.setInt(1, check); // チェック状態 (1: 完了, 0: 未完了)
	        stmt.setInt(2, taskId); // タスクID

	        int rowsUpdated = stmt.executeUpdate(); // 更新された行数を取得
	        return rowsUpdated > 0; // 1行以上更新されたらtrue
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // エラーが発生した場合はfalseを返す
	    }
	}

	public List<Tasks> findByCurrentMonth(int accountId , int currentMonth) {
	    List<Tasks> taskList = new ArrayList<>();
	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {


	        // SQL文でtask_datetimeの月部分のみを比較
	        String sql = "SELECT * FROM tasks WHERE account_id = ? AND EXTRACT(MONTH FROM task_datetime) = ?";
	        PreparedStatement stmt = cn.prepareStatement(sql);
	        stmt.setInt(1, accountId);
	        stmt.setInt(2, currentMonth);  // 今日の月をSQLに渡す

	        ResultSet rs = stmt.executeQuery();

	        // データをリストに格納
	        while (rs.next()) {
	            taskList.add(rs2model(rs));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return taskList;
	}
	
	public Tasks findByTask(String task_name, LocalDateTime taskDatetime, int category_id) {
		Tasks tasks = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM tasks WHERE task_name = ? AND task_datetime = ? AND category_id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, task_name);
			stmt.setTimestamp(2, Timestamp.valueOf(taskDatetime));
			stmt.setInt(3, category_id);
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
	public Tasks create(int category_id, int account_id, String task_name, String memo, int outin, LocalDateTime taskDatetime) {
		int ret = -1;
		
		// 重複確認 → タスク名+タスク時間が重複でメッセージ出す
		if (findByTask(task_name, taskDatetime, category_id) != null) {
			System.out.println("該当タスクは既に存在しています");
			return null;
		}
		
		// DBにデータを追加
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			
			// プレースホルダで変数部分を定義
			String sql = "INSERT INTO tasks (category_id, account_id, task_name, memo, outin, task_datetime) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, category_id);
			stmt.setInt(2, account_id);
			stmt.setString(3, task_name);
			stmt.setString(4, memo);
			stmt.setInt(5, outin);
			stmt.setTimestamp(6, Timestamp.valueOf(taskDatetime));
			
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
        int checkTask =rs.getInt("check_Task");

        return new Tasks(id, category_id, account_id, task_name, taskDatetime, memo, createdAt, updateDate, outin, checkTask);
    }

	public Tasks create(String date, String time, String category, String taskName, String memo) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	public boolean insertPresetTasks(List<PresetTasks> presetTasks, int accountId, String date) {
        String sql = "INSERT INTO tasks (category_id, account_id, task_name, memo, outin, task_datetime) VALUES (?, ?, ?, ?, ?, ?)";
        boolean result = false;

        DBManager manager = DBManager.getInstance();
        try (Connection conn = manager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // バッチ処理の開始
            for (PresetTasks task : presetTasks) {
            	String datetime = date + " " + task.getTaskTime();
                LocalDateTime taskDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            	
                stmt.setInt(1, task.getCategoryId());
                stmt.setInt(2, accountId);
                stmt.setString(3, task.getName());
                stmt.setString(4, task.getMemo());
                stmt.setInt(5, task.getOutin());
                stmt.setTimestamp(6, Timestamp.valueOf(taskDateTime));

                // バッチに追加
                stmt.addBatch();
            }

            // バッチ処理を実行
            int[] insertCounts = stmt.executeBatch();
            result = insertCounts.length == presetTasks.size();  // すべての挿入が成功した場合
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    } 


	public Tasks findById(int task_id, int account_id) {
    	Tasks tasks = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM tasks WHERE id = ? AND account_id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, task_id);
			stmt.setInt(2, account_id);
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
	
	public boolean deleteTask(int accountId, int taskId) {
	    int ret = -1;
	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        // SQL文をDELETE文に変更
	        String sql = "DELETE FROM tasks WHERE id = ?  AND account_id = ?";
	        PreparedStatement stmt = cn.prepareStatement(sql);
	        // プレースホルダーに値を設定
	        
	        stmt.setInt(1, taskId);
	        stmt.setInt(2, accountId);
	        // 実行結果を取得
	        ret = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    // 更新された行が1行以上かどうかを返す
	    return ret > 0;
	}

	// タスク更新メソッド
	public boolean updateTask(int taskId, int category_id, int account_id, String task_name, String memo, int outin, LocalDateTime taskDatetime ) {
	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        String sql = "UPDATE tasks SET　category_id　= ?, account_id = ?, task_name = ?, memo = ?, outin = ?, task_datetime = ? WHERE id = ?";
	        PreparedStatement stmt = cn.prepareStatement(sql);
	    
	        stmt.setInt(1, category_id);
	        stmt.setInt(2, account_id);
	        stmt.setString(3, task_name);
	        stmt.setString(4, memo);
	        stmt.setInt(5, outin);
	        stmt.setTimestamp(6, Timestamp.valueOf(taskDatetime));
	        stmt.setInt(7, taskId);

	        int rowsUpdated = stmt.executeUpdate();  // 更新された行数を取得
	        return rowsUpdated > 0; // 1行以上更新されたらtrue
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // エラー発生時
	    }
	}
	
	public List<Tasks> findTaskTime(LocalDateTime dateTime) {
		List<Tasks> list = new ArrayList<>();
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM tasks WHERE task_datetime = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setTimestamp(1, Timestamp.valueOf(dateTime));
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Tasks tasks = rs2model(rs);
				list.add(tasks);
				System.out.println("タスクあるよ");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
