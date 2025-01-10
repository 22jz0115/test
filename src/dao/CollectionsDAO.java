package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Collections;

public class CollectionsDAO {
	public List<Collections> get() {
		List<Collections> list = new ArrayList<>();
				
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			String sql = "SELECT * FROM collections ORDER BY 1";
			PreparedStatement stmt = cn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			while(rs.next()) {
				Collections collections = rs2model(rs);
				list.add(collections);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return list;
    }
	
	public Collections find(int id) {
    	Collections collection = null;
		DBManager manager = DBManager.getInstance();
		try(Connection cn = manager.getConnection()) {
			// プレースホルダで変数部分を定義
			String sql = "SELECT * FROM collections WHERE id = ?";
			PreparedStatement stmt = cn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			// データをリストに格納
			if (rs.next()) {
				collection = rs2model(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return collection;
    }
	
	public List<Collections> findByCollectionList(List<Integer> collectionIds) {
	    List<Collections> collectList = new ArrayList<>();
	    DBManager manager = DBManager.getInstance();

	    // 引数が空の場合、すぐに空のリストを返す
	    if (collectionIds == null || collectionIds.isEmpty()) {
	        return collectList;
	    }

	    try (Connection cn = manager.getConnection()) {
	        // IN句のプレースホルダを動的に生成
	        String placeholders = String.join(",", collectionIds.stream().map(id -> "?").toArray(String[]::new));
	        String sql = "SELECT * FROM collections WHERE id IN (" + placeholders + ")";
	        PreparedStatement stmt = cn.prepareStatement(sql);

	        // プレースホルダに値をセット
	        for (int i = 0; i < collectionIds.size(); i++) {
	            stmt.setInt(i + 1, collectionIds.get(i));
	        }

	        ResultSet rs = stmt.executeQuery();

	        // データをリストに格納
	        while (rs.next()) {
	            Collections collect = rs2model(rs);
	            collectList.add(collect);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return collectList;
	}
	
	private Collections rs2model(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String img = rs.getString("img");
        LocalDateTime createdAt =
                 rs.getTimestamp("created_at").toLocalDateTime();                          
        LocalDateTime updateDate = 
                 rs.getTimestamp("update_date").toLocalDateTime();  

        return new Collections(id, img, createdAt, updateDate);
    }
}