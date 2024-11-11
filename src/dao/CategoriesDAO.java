package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Categories;

public class CategoriesDAO {
	public List<Categories> get() {
		List<Categories> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM categories";
			PreparedStatement stmt = cn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				Categories categories = rs2model(rs);
				list.add(categories);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	public Categories find(int id) {
    	Categories categories = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM categories WHERE id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				categories = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return categories;
    }
	
	/**
	 * テーブルの中から、指定された名前を持つレコードを返すメソッド
	 * @param category_name カテゴリ名 
	 * @return 発見したデータ。なければnull
	 */
	public Categories findByName(String category_name) {
		Categories categories = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM categories WHERE category_name = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setString(1, category_name);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				categories = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return categories;
	}
	
	private Categories rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String category_name = rs.getString("category_name");
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime();  

        return new Categories(id, category_name, createdAt, updateDate);
    }
}
