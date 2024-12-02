package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.MyBoxs;

public class MyBoxesDAO {
	public List<MyBoxs> get() {
		List<MyBoxs> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM my_boxes";
			PreparedStatement stmt = cn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				MyBoxs boxs = rs2model(rs);
				list.add(boxs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	public List<MyBoxs> findByAccountId(int account_Id) {
		List<MyBoxs> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM my_boxes WHERE account_id = ?";
			
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, account_Id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				MyBoxs boxs = rs2model(rs);
				list.add(boxs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	
	public MyBoxs find(int Id) {
    	MyBoxs boxs = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM my_boxes WHERE id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, Id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				boxs = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return boxs;
    }
	
	public MyBoxs find(int account_Id, int collection_Id) {
    	MyBoxs boxs = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM my_boxes WHERE account_Id = ? AND collection_Id = ?";

			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, account_Id);
			stmt.setInt(2, collection_Id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				boxs = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return boxs;
    }
	

	/**
	 * DBにデータを追加する
	 * @return 成功時は追加したデータ、失敗時はnull
	 */
	public MyBoxs create(int account_Id, int collection_Id) {
		int ret = -1;
		
		// 重複確認 → タスク名+タスク時間が重複でメッセージ出す
		if (find(account_Id, collection_Id) != null) {
			System.out.println("既に存在しています");
			return null;
		}
		
	
		
		// DBにデータを追加
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			
			// プレースホルダで変数部分を定義
			String sql = "INSERT INTO my_boxes (account_id, collection_id) VALUES (?, ?)";
			PreparedStatement stmt = cn.prepareStatement(sql);
		
			stmt.setInt(1, account_Id);
			stmt.setInt(2, collection_Id);
			
			ret = stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if (ret >= 0) {
			return find(account_Id, collection_Id);
		}
		return null;
	}
	
	private MyBoxs rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int account_id = rs.getInt("account_id");
        int collection_id = rs.getInt("collection_id");
       
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime();  

        return new MyBoxs(id, account_id, collection_id, createdAt, updateDate);
    }
}
