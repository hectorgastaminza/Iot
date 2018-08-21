package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.server.User;
import database.mysql.ConnectorMysql;

import java.sql.ResultSet;

public class DBConnector {

	private static User dbCreateUser(ResultSet rs) throws SQLException {
		User user = null;
		
		if(rs.next()) {
			user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"));
		}
		
		return user;
	}
	
	private static boolean userDataValidator(Connection conn, int pk, User user) throws SQLException {
		boolean retval = false;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT * FROM user where ((username = ? or email = ? ) and (pk_user_id != ?))";
			PreparedStatement p = conn.prepareStatement(query);
			p.setString(1, user.getUsername());
			p.setString(2, user.getEmail());
			p.setInt(3, pk);
			ResultSet rs = p.executeQuery();
			
			if(!rs.next()) {
				retval = true;
			}
		}
		
		return retval;
	}
	
	public static int userGetPk(Connection conn, String username, String password) throws SQLException {
		int retval = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT pk_user_id FROM user WHERE username=? and password=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setString(1, username);
			p.setString(2, password);
			ResultSet rs = p.executeQuery();
			
			if(rs.next()) {
				int pk_user_id = rs.getInt("pk_user_id");
				System.out.println("connected : " + pk_user_id + ", " + username + ", " + password);
				retval = pk_user_id;
			}
		}
		
		return retval;
	}
	
	public static User userGetByPk(Connection conn, int pk) throws SQLException {
		User user = null;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT * FROM user WHERE pk_user_id=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, pk);
			ResultSet rs = p.executeQuery();
			
			user = dbCreateUser(rs);
		}
		
		return user;
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
			if(userDataValidator(conn, -1, user)) {
				String query = "INSERT INTO user (username, password, email) values (?, ?, ?)";
				PreparedStatement p = conn.prepareStatement(query);
				p.setString(1, user.getUsername());
				p.setString(2, user.getPassword());
				p.setString(3, user.getEmail());
				retval = p.executeUpdate();
				
				/*
				 * // fill in the prepared statement and
					pInsertOid.executeUpdate();
					ResultSet rs = pInsertOid.getGeneratedKeys();
					if (rs.next()) {
					  int newId = rs.getInt(1);
					  oid.setId(newId);
					}
				 */
				
				if(retval > 0) {
					System.out.println("inserted : " + retval);
				}
			}
		}
		
		return retval;
	}

	public static int userUpdate(Connection conn, int pk, User user) throws SQLException{
		int result = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			if(userDataValidator(conn, pk, user)) {
				String query = "UPDATE user SET username = ?, password = ?, email = ? where pk_user_id = ?";
				PreparedStatement p = conn.prepareStatement(query);
				p.setString(1, user.getUsername());
				p.setString(2, user.getPassword());
				p.setString(3, user.getEmail());
				p.setInt(4, pk);
				result = p.executeUpdate();
			}
		}
		
		return result;
	}
}
