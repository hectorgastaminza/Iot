package database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorMysql {
	
	public static boolean ConnectorMysql(String username, String password) {
		boolean retval = false;

		try {
			// The newInstance() call is a work around for some
			// broken Java implementations

			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			// handle the error
			System.out.println(ex.toString());
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/comiot?" +
					"user=comiot&password=123456");
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
