package comiot.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comiot.core.application.server.Place;
import comiot.core.application.server.User;
import comiot.core.device.Device;
import comiot.core.device.eDeviceStates;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

import java.sql.ResultSet;

public class DBConnector {

	/* ---------------------------------------------------- */
	/* ------------------ USER ---------------------------- */
	/* ---------------------------------------------------- */
	
	private static User dbCreateUser(ResultSet rs) throws SQLException {
		User user = null;
		
		if(rs.next()) {
			user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"));
			user.setPk(rs.getInt("pk_user_id"));
		}
		
		return user;
	}
	
	private static boolean userDataValidator(Connection conn, int pk, User user) throws SQLException {
		boolean retval = false;
		
		if((conn != null) && (conn.isValid(0)))
		{
			if((!user.getUsername().equals("")) && (!user.getPassword().equals(""))) {
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
		}
		
		return retval;
	}
	
	public static User userGetPk(Connection conn, String username, String password) throws SQLException {
		User user = null;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT * FROM user WHERE username=? and password=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setString(1, username);
			p.setString(2, password);
			ResultSet rs = p.executeQuery();
			
			user = dbCreateUser(rs);
		}
		
		return user;
	}
	
	public static List<Integer> usersGetPk(Connection conn) throws SQLException {
		List<Integer> users = null;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT pk_user_id FROM user";
			PreparedStatement p = conn.prepareStatement(query);
			ResultSet rs = p.executeQuery();
			
			if(rs.next()) {
				users = new ArrayList<>();
				users.add(rs.getInt("pk_user_id"));
				while (rs.next()) {
					users.add(rs.getInt("pk_user_id"));
				}
			}
		}
		
		return users;
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
				PreparedStatement p = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				p.setString(1, user.getUsername());
				p.setString(2, user.getPassword());
				p.setString(3, user.getEmail());
				retval = p.executeUpdate();

				if(retval > 0) {
					System.out.println("inserted : " + retval);
					ResultSet rs = p.getGeneratedKeys();
					if (rs.next()) {
						user.setPk(rs.getInt(1));
					}
				}
			}
		}
		
		return retval;
	}

	public static int userUpdate(Connection conn, User user) throws SQLException{
		int result = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			if(userDataValidator(conn, user.getPk(), user)) {
				String query = "UPDATE user SET username = ?, password = ?, email = ? where pk_user_id = ?";
				PreparedStatement p = conn.prepareStatement(query);
				p.setString(1, user.getUsername());
				p.setString(2, user.getPassword());
				p.setString(3, user.getEmail());
				p.setInt(4, user.getPk());
				result = p.executeUpdate();
			}
		}
		
		return result;
	}

	
	/* ---------------------------------------------------- */
	/* ------------------ CONNECTION ---------------------- */
	/* ---------------------------------------------------- */

	private static MqttConnectionConfiguration dbCreateMqttConfig(ResultSet rs) throws SQLException {
		MqttConnectionConfiguration config = null;
		
		if(rs.next()) {
			config = new MqttConnectionConfiguration(
					rs.getString("host"), 
					rs.getInt("port"), 
					rs.getString("username"),
					rs.getString("password"),
					rs.getString("root_topic"));
			config.setPk(rs.getInt("pk_connection_id"));
		}
		
		return config;
	}

	public static MqttConnectionConfiguration mqttConfigGetByUserPk(Connection conn, int userPk) throws SQLException {
		MqttConnectionConfiguration retval = null;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT * FROM connection WHERE pk_user_id=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, userPk);
			ResultSet rs = p.executeQuery();
			
			retval = dbCreateMqttConfig(rs);
		}
		
		return retval;
	}

	private static int mqttConfigInsert(Connection conn, int userPk, MqttConnectionConfiguration config) throws SQLException {
		int retval = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "INSERT INTO connection (pk_user_id, host, port, username, password, root_topic) values (?, ?, ?, ?, ?, ?)";
			PreparedStatement p = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			p.setInt(1, userPk);
			p.setString(2, config.getBrokerHost());
			p.setInt(3, config.getBrokerPort());
			p.setString(4, config.getUsername());
			p.setString(5, config.getPasswordStr());
			p.setString(6, config.getRootTopic());
			retval = p.executeUpdate();

			if(retval > 0) {
				ResultSet rs = p.getGeneratedKeys();
				if (rs.next()) {
					config.setPk(rs.getInt(1));
				}
			}
		}
		
		return retval;
	}
	
	private static int mqttConfigUpdate(Connection conn, MqttConnectionConfiguration config) throws SQLException{
		int result = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "UPDATE connection "
					+ "SET host=?, port=?, username=?, password=?, root_topic=? "
					+ "where pk_connection_id=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setString(1, config.getBrokerHost());
			p.setInt(2, config.getBrokerPort());
			p.setString(3, config.getUsername());
			p.setString(4, config.getPasswordStr());
			p.setString(5, config.getRootTopic());
			p.setInt(6, config.getPk());
			result = p.executeUpdate();
		}
		
		return result;
	}
	
	public static int mqttConfigRefresh(Connection conn, int userPk, MqttConnectionConfiguration config) throws SQLException {
		int retval = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			MqttConnectionConfiguration aux = DBConnector.mqttConfigGetByUserPk(conn, userPk);
			if(aux != null) {
				config.setPk(aux.getPk());
				retval = DBConnector.mqttConfigUpdate(conn, config);
			}
			else {
				retval = DBConnector.mqttConfigInsert(conn, userPk, config);
			}
		}
		
		return retval;
	}

	public static int mqttConfigDelete(Connection conn, int userPk) throws SQLException {
		int result = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "DELETE FROM connection "
					+ "where pk_user_id=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, userPk);
			result = p.executeUpdate();
		}
		
		return result;
	}

	
	/* ---------------------------------------------------- */
	/* ------------------ PLACE --------------------------- */
	/* ---------------------------------------------------- */

	private static List<Place> dbCreatePlaces(ResultSet rs) throws SQLException {
		List<Place> places = new ArrayList<>();;
		
		while(rs.next()) {
			Place place = new Place(rs.getInt("place_id"));
			place.setPlaceName(rs.getString("place_name"));
			place.setDescription(rs.getString("place_description"));
			place.setPk(rs.getInt("pk_place_id"));
			places.add(place);
		}
		
		return places;
	}

	public static List<Place> placesGetByUserPk(Connection conn, int userPk) throws SQLException {
		List<Place> retval = null;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT * FROM place WHERE pk_user_id=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, userPk);
			ResultSet rs = p.executeQuery();
			
			retval = dbCreatePlaces(rs);
		}
		
		return retval;
	}
	
	public static int placeDelete(Connection conn, int placePk) throws SQLException {
		int result = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "DELETE FROM place "
					+ "where pk_place_id=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, placePk);
			result = p.executeUpdate();
		}
		
		return result;
	}
	
	
	/* ---------------------------------------------------- */
	/* ------------------ DEVICE -------------------------- */
	/* ---------------------------------------------------- */

	private static List<Device> dbCreateDevices(ResultSet rs) throws SQLException {
		List<Device> devices = new ArrayList<>();;
		
		while(rs.next()) {
			Device device = new Device(rs.getInt("place_id"), rs.getInt("device_id"));
			device.setName(rs.getString("device_name"));
			device.setDescription(rs.getString("device_description"));
			device.setState(eDeviceStates.getFromValue(rs.getInt("device_state")), rs.getInt("device_value"));
			device.setPk(rs.getInt("pk_device_id"));
			devices.add(device);
		}
		
		return devices;
	}

	public static List<Device> devicesGetByUserPk(Connection conn, int userPk) throws SQLException {
		List<Device> retval = null;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT * FROM device WHERE pk_user_id=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, userPk);
			ResultSet rs = p.executeQuery();
			
			retval = dbCreateDevices(rs);
		}
		
		return retval;
	}
	
	public static Device deviceGetByPk(Connection conn, int devicePk) throws SQLException {
		Device retval = null;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "SELECT * FROM device WHERE pk_device_id=? ORDER BY device_id ASC";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, devicePk);
			ResultSet rs = p.executeQuery();
			
			List<Device> aux = dbCreateDevices(rs);
			if(aux != null) {
				retval = aux.get(0);
			}
		}
		
		return retval;
	}
	
	public static int deviceDelete(Connection conn, int devicePk) throws SQLException {
		int result = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "DELETE FROM device "
					+ "where pk_device_id=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, devicePk);
			result = p.executeUpdate();
		}
		
		return result;
	}
	
	public static int deviceInsert(Connection conn, int userPk, Device device) throws SQLException {
		int retval = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "INSERT INTO device (pk_user_id, device_id, device_name, device_description) values (?, ?, ?, ?)";
			PreparedStatement p = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			p.setInt(1, userPk);
			p.setInt(2, device.getId());
			p.setString(3, device.getName());
			p.setString(4, device.getDescription());
			retval = p.executeUpdate();

			if(retval > 0) {
				ResultSet rs = p.getGeneratedKeys();
				if (rs.next()) {
					device.setPk(rs.getInt(1));
				}
			}
		}
		
		return retval;
	}
	
	public static int deviceUpdate(Connection conn, Device device) throws SQLException{
		int result = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "UPDATE device "
					+ "SET device_id=?, device_name=?, device_description=? "
					+ "where pk_device_id=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, device.getId());
			p.setString(2, device.getName());
			p.setString(3, device.getDescription());
			p.setInt(4, device.getPk());
			result = p.executeUpdate();
		}
		
		return result;
	}
	
	public static int deviceUpdateState(Connection conn, int userPk, Device device) throws SQLException{
		int result = -1;
		
		if((conn != null) && (conn.isValid(0)))
		{
			String query = "UPDATE device "
					+ "SET device_state=?, device_value=? "
					+ "where pk_user_id=? and "
					+ "device_id=? and "
					+ "place_id=?";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, device.getState().getValue());
			p.setInt(2, device.getValue());
			p.setInt(3, userPk);
			p.setInt(4, device.getId());
			p.setInt(5, device.getPlaceID());
			result = p.executeUpdate();
		}
		
		return result;
	}
}
