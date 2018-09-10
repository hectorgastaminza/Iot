package comiot.core.database.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.cj.jdbc.MysqlDataSource;

public class ConnectorMysql {
	private static final String DATABASE_URL = "localhost";
	private static final String DATABASE_NAME = "comiot?allowPublicKeyRetrieval=true&useSSL=FALSE";
	private static final String USERNAME = "comiot";
	private static final String PASSWORD = "123456";
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser(USERNAME);
			dataSource.setPassword(PASSWORD);
			dataSource.setDatabaseName(DATABASE_NAME);
			dataSource.setServerName(DATABASE_URL);

			conn = dataSource.getConnection();
			if(!conn.isValid(0))
			{
				conn = null;
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return conn;
	}
}
