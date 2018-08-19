package database.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ConnectorMysql {
	
	// init database constants
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "localhost";
	private static final String DATABASE_NAME = "comiot";
	private static final String USERNAME = "comiot";
	private static final String PASSWORD = "123456";
	private static final String MAX_POOL = "250"; // set your own limit
	
	public static boolean connect(String username, String password) {
		boolean retval = false;

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser(USERNAME);
		dataSource.setPassword(PASSWORD);
		dataSource.setDatabaseName(DATABASE_NAME);
		dataSource.setServerName(DATABASE_URL);

		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			if(conn.isValid(0))
			{
				String query = "SELECT pk_user_id FROM user WHERE username=? or password=?";
				PreparedStatement p = conn.prepareStatement(query);
				p.setString(1, username);
				p.setString(2, password);
				ResultSet rs = p.executeQuery();
				
				if(rs.next()) {
					int pk_user_id = rs.getInt("pk_user_id");

					System.out.println("connected : " + pk_user_id);
					retval = true;
				}
			}

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return retval;
	}
}
