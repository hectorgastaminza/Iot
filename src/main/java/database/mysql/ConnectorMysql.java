package database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorMysql {
	
	// init database constants
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/comiot?";
	private static final String USERNAME = "comiot";
	private static final String PASSWORD = "123456";
	private static final String MAX_POOL = "250"; // set your own limit
	
	public static boolean ConnectorMysql(String username, String password) {
		boolean retval = false;

		try {
			// The newInstance() call is a work around for some
			// broken Java implementations

			Class.forName(DATABASE_DRIVER).newInstance();
		} catch (Exception ex) {
			// handle the error
			System.out.println(ex.toString());
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DATABASE_URL +
					"user="+USERNAME+"&password="+PASSWORD);
			if(conn.isValid(0))
			{
				System.out.println("connected");
				retval = true;
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
