package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Subscriptions;

public class SubscriptionsDAO {
	public List<Subscriptions> get() {
		List<Subscriptions> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM subscriptions";
			PreparedStatement stmt = cn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				Subscriptions subscriptions = rs2model(rs);
				list.add(subscriptions);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	public Subscriptions find(int id) {
		Subscriptions subscriptions = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM subscriptions WHERE id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				subscriptions = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return subscriptions;
    }
	
	/**
	 * テーブルの中から、指定されたメールアドレスを持つレコードを返すメソッド
	 * @param email メールアドレス
	 * @return 発見したデータ。なければnull
	 */
	public Subscriptions findByAccount(int account_id) {
		Subscriptions subscriptions = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM subscriptions WHERE account_id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, account_id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				subscriptions = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return subscriptions;
	}
	
	/**
	 * DBにデータを追加する
	 * @return 成功時は追加したデータ、失敗時はnull
	 */
	public Subscriptions create(int accountId, String endPoint) {
		int ret = -1;
		
		// 重複確認
		if (findByAccount(accountId) != null) {
			System.out.println("該当ユーザは既に存在しています");
			return null;
		}
		
		// DBにデータを追加
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			
			// プレースホルダで変数部分を定義
			String sql = "INSERT INTO subscriptions (account_id, end_point) VALUES (?, ?)";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, accountId);
			stmt.setString(2, endPoint);
			
			ret = stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if (ret >= 0) {
			return findByAccount(accountId);
		}
		return null;
	}
	
	public boolean deleteSubscribe(int accountId) {
	    int ret = -1;
	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        // SQL文をDELETE文に変更
	        String sql = "DELETE FROM subscriptions WHERE account_id = ?";
	        PreparedStatement stmt = cn.prepareStatement(sql);
	        stmt.setInt(1, accountId);
	        // 実行結果を取得
	        ret = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    // 更新された行が1行以上かどうかを返す
	    return ret > 0;
	}
	
	private Subscriptions rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int accountId = rs.getInt("account_id");
        String endPoint = rs.getString("end_point");
        String p256dh = rs.getString("p256dh");
        String auth = rs.getString("auth");
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime();  

        return new Subscriptions(id, accountId, endPoint, p256dh, auth, createdAt, updateDate);
    }
}
