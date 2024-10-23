package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Options;

public class OptionsDAO {
	public List<Options> get() {
		List<Options> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM options";
			PreparedStatement stmt = cn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				Options options = rs2model(rs);
				list.add(options);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	public Options find(int id) {
    	Options options = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM options WHERE id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				options = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return options;
    }
	
	
	/**
	 * DBにデータを追加する
	 * @return 成功時は追加したデータ、失敗時はnull
	 */
	public Options create(int account_id, int bluetooth, int message, String text_color, String background_color) {
		int ret = -1;
		
		
		// DBにデータを追加
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			
			// プレースホルダで変数部分を定義
			String sql = "INSERT INTO options (account_id, bluetooth, message, text_color, background_color) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, account_id);
			stmt.setInt(2, bluetooth);
			stmt.setInt(3, message);
			stmt.setString(4, text_color);
			stmt.setString(5, background_color);
			
			ret = stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Options rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int account_id = rs.getInt("account_id");
        boolean bluetooth = rs.getBoolean("bluetooth");
        boolean message = rs.getBoolean("message");
        String text_color = rs.getString("text_color");
        String background_color = rs.getString("background_color");
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime();  

        return new Options(id, account_id, bluetooth, message, text_color, background_color, createdAt, updateDate);
    }
}
