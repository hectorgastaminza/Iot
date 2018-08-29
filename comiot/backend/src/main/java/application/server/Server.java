package application.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.common.AppConnection;
import database.DBConnector;
import database.mysql.ConnectorMysql;
import device.Device;
import device.command.DeviceCommandRefreshState;
import protocol.mqtt.MqttConnectionConfiguration;

public class Server implements IDeviceStatusRefreshCallback {
	private HashMap<Integer, DeviceServer> usersDeviceServer = null;
	
	
	public Server() {
		usersDeviceServer = new HashMap<>();
	}
	
	public void run( ) {
		loadUsers();
		
		for (Map.Entry<Integer, DeviceServer> entry : usersDeviceServer.entrySet()) {
			loadPlaces(entry.getKey());
			loadDevices(entry.getKey());
			loadConnection(entry.getKey());
			entry.getValue().connect();
		}
	}
	
	/**
	 * Load users from database
	 * @return
	 * @throws SQLException 
	 */
	public boolean loadUsers() {
		boolean retval = false;
		List<Integer> users = null;
		
		Connection conn = ConnectorMysql.getConnection();
		
		try {
			users = DBConnector.usersGetPk(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(users != null) {
			retval = true;
			deleteUsers();
			for (Integer user : users) {
				addUser(user);
			}
		}
		
		try {
			if(conn!=null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retval;
	}
	
	public boolean loadPlaces(int userPk) {
		boolean retval = false;
		
		if(usersDeviceServer.containsKey(userPk)) {
			List<Place> places = null;
			
			Connection conn = ConnectorMysql.getConnection();
			
			try {
				places = DBConnector.placesGetByUserPk(conn, userPk);
				for (Place place : places) {
					addPlace(userPk, place);
				}
				retval = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return retval;
	}

	public boolean loadDevices(int userPk) {
		boolean retval = false;
		
		if(usersDeviceServer.containsKey(userPk)) {
			List<Device> devices = null;
			
			Connection conn = ConnectorMysql.getConnection();
			
			try {
				devices = DBConnector.devicesGetByUserPk(conn, userPk);
				for (Device device : devices) {
					addDevice(userPk, device);
				}
				retval = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return retval;
	}
	
	public boolean loadConnection(int userPk) {
		boolean retval = false;
		
		if(usersDeviceServer.containsKey(userPk)) {
			MqttConnectionConfiguration mqttConfig = null;
			
			Connection conn = ConnectorMysql.getConnection();
			
			try {
				mqttConfig = DBConnector.mqttConfigGetByUserPk(conn, userPk);
				retval = addMqttConfig(userPk, mqttConfig);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return retval;
	}

	public void deleteUsers() {
		if((usersDeviceServer != null) && (!usersDeviceServer.isEmpty())) {
			// Closing connections
			for (Map.Entry<Integer, DeviceServer> entry : usersDeviceServer.entrySet()) {
				entry.getValue().disconnect();
			}
			// clear users list
			usersDeviceServer.clear();
		}
	}
	
	private boolean addUser(Integer userPk) {
		boolean retval = false;
		
		if(!usersDeviceServer.containsKey(userPk)) {
			DeviceServer deviceServer = new DeviceServer();
			deviceServer.setUserId(userPk);
			deviceServer.setDeviceStatusRefreshCallback(this);
			usersDeviceServer.put(userPk, deviceServer);
			retval = true;
		}
		
		return retval;
	}
	
	private boolean addPlace(Integer userPk, Place place) {
		boolean retval = false;
		
		if((usersDeviceServer != null) && (usersDeviceServer.containsKey(userPk))) {
			retval = usersDeviceServer.get(userPk).addPlace(place);
		}
		
		return retval;
	}
	
	private boolean addDevice(int userPk, Device device) {
		boolean retval = false;
		
		if((usersDeviceServer != null) && (usersDeviceServer.containsKey(userPk))) {
			retval = usersDeviceServer.get(userPk).addDevice(device);
		}
		
		return retval;
	}
	
	private boolean addMqttConfig(int userPk, MqttConnectionConfiguration mqttConfig) {
		boolean retval = false;
		
		if((usersDeviceServer != null) && (usersDeviceServer.containsKey(userPk))) {
			usersDeviceServer.get(userPk).disconnect();
			usersDeviceServer.get(userPk).setAppConnection(new AppConnection(mqttConfig));
			retval = true;
		}
		
		return retval;
	}

	@Override
	public void deviceRefreshStatusCallback(int userPk, DeviceCommandRefreshState deviceCommandRefreshState) {
		System.out.println("UserPk: " + userPk + " | D : " + deviceCommandRefreshState.getDeviceID() +
				" | P : " + deviceCommandRefreshState.getPlaceID() + 
				" | S : " + deviceCommandRefreshState.getState() + 
				" | V : " + deviceCommandRefreshState.getValue());
		
		if(usersDeviceServer.containsKey(userPk)) {
			Connection conn = ConnectorMysql.getConnection();
			
			try {
				Device device = new Device(deviceCommandRefreshState.getPlaceID(), deviceCommandRefreshState.getDeviceID());
				device.setState(deviceCommandRefreshState.getState(), deviceCommandRefreshState.getValue());
				DBConnector.deviceUpdateState(conn, userPk, device);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
