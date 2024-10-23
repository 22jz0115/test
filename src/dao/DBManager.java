package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	
		private static final String jdbcUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=tcps)(HOST=adb.ap-tokyo-1.oraclecloud.com)(PORT=1522))(CONNECT_DATA=(SERVICE_NAME=ga5fa430369d2d1_team03atp01_low.adb.oraclecloud.com)))";
		private static final String username = "TEST";
		private static final String password = "fh-p*KP2&C*QV#vdh4*2";
        
        private static DBManager self;
        
        private DBManager() {
        	try {
    			Class.forName("oracle.jdbc.driver.OracleDriver");
    		} catch(ClassNotFoundException e) {
    			return;
    		}
        }
        
        public static DBManager getInstance() {
    		if (self == null) {// まだ一度もインスタンス化してなければ
    			self = new DBManager();
    		}
    		return self;
    	}
        
        protected Connection getConnection() throws SQLException {
    		return DriverManager.getConnection(jdbcUrl, username, password);
    	}

}
