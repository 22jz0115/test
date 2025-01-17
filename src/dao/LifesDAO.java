package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Lifes;


public class LifesDAO {
	public List<Lifes> get() {
		List<Lifes> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM lifes";
			PreparedStatement stmt = cn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				Lifes lifes = rs2model(rs);
				list.add(lifes);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	public List<Lifes> getByAccountId(int account_id) {
		List<Lifes> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM lifes WHERE account_id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, account_id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				Lifes lifes = rs2model(rs);
				list.add(lifes);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	public Lifes find(int id) {
    	Lifes lifes = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM lifes WHERE id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				lifes = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return lifes;
    }
	
	/**
	 * 検索キーワードに合致するものを取得する
	 * @return 成功時は追加したデータ、失敗時はnull
	 */
	public List<Lifes> searchByKeyword(String keyword) {
	    List<Lifes> list = new ArrayList<>();
	    DBManager manager = DBManager.getInstance();

	    try (Connection cn = manager.getConnection()) {
	        String sql = "SELECT * FROM lifes WHERE content LIKE ?";
	        PreparedStatement stmt = cn.prepareStatement(sql);
	        stmt.setString(1, "%" + keyword + "%");
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Lifes lifes = rs2model(rs);
	            list.add(lifes);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	/**
	 * DBにデータを追加する
	 * @return 成功時は追加したデータ、失敗時はnull
	 */
	public Lifes create(int account_id, String title, String img, String content) {
		int ret = -1;
		
		
		// DBにデータを追加
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			
			// プレースホルダで変数部分を定義
			String sql = "INSERT INTO lifes (account_id, title, img, content) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, account_id);
			stmt.setString(2, title);
			stmt.setString(3, img);
			stmt.setString(4, content);
			
			ret = stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * DBにデータを追加する
	 * @return 成功時は追加したデータ、失敗時はnull
	 */
	public Lifes lifeChange(int id, String title, String img, String content) {
	    int ret = -1;
	    Lifes lifes = null;

	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        // 正しいSQL文に修正
	        String sql = "UPDATE lifes SET title = ?, img = ?, content = ? WHERE id = ?";
	        PreparedStatement stmt = cn.prepareStatement(sql);

	        // プレースホルダに値をセット
	        stmt.setString(1, title);
	        stmt.setString(2, img);
	        stmt.setString(3, content);
	        stmt.setInt(4, id);

	        ret = stmt.executeUpdate();

	        // 更新が成功した場合はLifesオブジェクトを返す
	        if (ret > 0) {
	            lifes = new Lifes(id, 0, title, img, content, null, null, null); // 仮の値
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return lifes;
	}
	
	public Lifes lifeChangeText(int id, String title, String content) {
	    int ret = -1;
	    Lifes lifes = null;

	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        // 正しいSQL文に修正
	        String sql = "UPDATE lifes SET title = ?, content = ? WHERE id = ?";
	        PreparedStatement stmt = cn.prepareStatement(sql);

	        // プレースホルダに値をセット
	        stmt.setString(1, title);
	        stmt.setString(2, content);
	        stmt.setInt(3, id);

	        ret = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return lifes;
	}
	
	public boolean deleteLife(int accountId, int Id) {
	    int ret = -1;
	    DBManager manager = DBManager.getInstance();
	    try (Connection cn = manager.getConnection()) {
	        // SQL文をDELETE文に変更
	        String sql = "DELETE FROM lifes WHERE id = ?  AND account_id = ?";
	        PreparedStatement stmt = cn.prepareStatement(sql);
	        // プレースホルダーに値を設定
	        
	        stmt.setInt(1, Id);
	        stmt.setInt(2, accountId);
	        // 実行結果を取得
	        ret = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    // 更新された行が1行以上かどうかを返す
	    return ret > 0;
	}
	
	
	private Lifes rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int account_id = rs.getInt("account_id");
        String title = rs.getString("title");
        String img = rs.getString("img");
        String content = rs.getString("content");
        LocalDateTime postDate =
                rs.getTimestamp("post_date").toLocalDateTime();
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime();  

        return new Lifes(id, account_id, title, img, content, postDate, createdAt, updateDate);
    }
}
