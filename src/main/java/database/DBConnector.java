package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.server.User;

import java.sql.ResultSet;

public class DBConnector {

	private static User dbCreateUser(ResultSet rs) throws SQLException {
		User user = null;
		
		if(rs.next()) {
			user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"));
		}
		
		return user;
	}
	
	public static int userGetPk(Connection conn, String username, String password) throws SQLException {
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
	
	public static User userGetByUsername(Connection conn, String username) throws SQLException {
		User user = null;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT * FROM user WHERE username=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setString(1, username);
			ResultSet rs = p.executeQuery();
			
			user = dbCreateUser(rs);
		}
		
		return user;
	}
	
	public static User userGetByEmail(Connection conn, String email) throws SQLException {
		User user = null;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT * FROM user WHERE email=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setString(1, email);
			ResultSet rs = p.executeQuery();
			
			user = dbCreateUser(rs);
		}
		
		return user;
	}
	
	public static int userInsert(Connection conn, User user) throws SQLException {
		int retval = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "INSERT INTO user (username, password, email) values (?, ?, ?)";
			PreparedStatement p = conn.prepareStatement(query);
			p.setString(1, user.getUsername());
			p.setString(2, user.getPassword());
			p.setString(3, user.getEmail());
			retval = p.executeUpdate();
			
			if(retval > 0) {
				System.out.println("inserted : " + retval);
				/* noresponse@comiotproject.com */
				/* +MAIfrosklo3201 */
			}
		}
		
		return retval;
	}
	
}
