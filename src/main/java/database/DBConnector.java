package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DBConnector {

	public static int getUserId(Connection conn, String username, String password) throws SQLException {
		int retval = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT pk_user_id FROM user WHERE username=? or password=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setString(1, username);
			p.setString(2, password);
			ResultSet rs = p.executeQuery();
			
			if(rs.next()) {
				int pk_user_id = rs.getInt("pk_user_id");
				System.out.println("connected : " + pk_user_id);
				retval = pk_user_id;
			}
		}
		
		return retval;
	}
	
}
