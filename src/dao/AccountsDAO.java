package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import model.Accounts;

public class AccountsDAO {
	public List<Accounts> get() {
		List<Accounts> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM accounts";
			PreparedStatement stmt = cn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				Accounts accounts = rs2model(rs);
				list.add(accounts);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	public List<Accounts> findByAccountId(int account_id) {
		List<Accounts> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE id = account_id";
			PreparedStatement stmt = cn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				Accounts accounts = rs2model(rs);
				list.add(accounts);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	
	public Accounts find(int id) {
    	Accounts accounts = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM accounts WHERE id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				accounts = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return accounts;
    }
	

	
	/**
	 * テーブルの中から、指定されたメールアドレスを持つレコードを返すメソッド
	 * @param email メールアドレス
	 * @return 発見したデータ。なければnull
	 */
	public Accounts findByEmail(String email) {
		Accounts accounts = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM accounts WHERE email = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				accounts = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return accounts;
	}
	
	/**
	 * DBにデータを追加する
	 * @return 成功時は追加したデータ、失敗時はnull
	 */
	public Accounts create(String name, String email, String pass, String location) {
		int ret = -1;
		
		// 重複確認
		if (findByEmail(email) != null) {
			System.out.println("該当ユーザは既に存在しています");
			return null;
		}
		
		// DBにデータを追加
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// パスワードをハッシュ化
			String hashed = BCrypt.hashpw(pass, BCrypt.gensalt());
			
			// プレースホルダで変数部分を定義
			String sql = "INSERT INTO accounts (name, email, pass, location) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, hashed);
			stmt.setString(4, location);
			
			ret = stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if (ret >= 0) {
			return findByEmail(email);
		}
		return null;
	}
	
	public boolean updateLoginTime(int accountId) {
	    DBManager manager = DBManager.getInstance();
	    int result = 0;
	    try (Connection cn = manager.getConnection()) {
	        // 現在のタイムスタンプを取得
	        Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());

	        // SQLクエリで update_date を更新
	        String sql = "UPDATE accounts SET update_date = ? WHERE id = ?";
	        PreparedStatement stmt = cn.prepareStatement(sql);
	        stmt.setTimestamp(1, currentTimestamp);
	        stmt.setInt(2, accountId);

	        // 更新結果を取得
	        result = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    // 更新が成功すれば true、失敗すれば false を返す
	    return result > 0;
	}
	
	private Accounts rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String pass = rs.getString("pass");
        String location = rs.getString("location");
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime();  

        return new Accounts(id, name, email, pass, location, createdAt, updateDate);
    }
}
