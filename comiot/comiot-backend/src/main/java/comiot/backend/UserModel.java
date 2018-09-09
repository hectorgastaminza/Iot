package comiot.backend;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import comiot.core.application.common.AppConnection;
import comiot.core.application.server.DeviceServer;
import comiot.core.application.server.IDeviceStatusRefreshCallback;
import comiot.core.application.server.Place;
import comiot.core.application.server.User;
import comiot.core.database.DBConnector;
import comiot.core.database.mysql.ConnectorMysql;
import comiot.core.device.Device;
import comiot.core.device.command.DeviceCommandRefreshState;
import comiot.core.device.command.DeviceCommandRequest;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

@Service
public class UserModel implements IDeviceStatusRefreshCallback {
	private HashMap<Integer, DeviceServer> usersDeviceServer = null;
	
	public UserModel() {
		usersDeviceServer = new HashMap<>();
	}
	
	public User userLogin(User user) {
		User loguedUser = null;
		
		try {
			Connection conn = ConnectorMysql.getConnection();
			loguedUser = DBConnector.userGetPk(conn, user.getUsername(), user.getPassword());
			conn.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (loguedUser != null) {
		    if(addUser(loguedUser.getPk())) {
		    	UserModelLoadThread runLoad = new UserModelLoadThread(usersDeviceServer.get(loguedUser.getPk()), loguedUser.getPk());
		        ExecutorService executor = Executors.newCachedThreadPool();
		        executor.submit(runLoad);
		    }
		}
		
		return loguedUser;
	}
	
	
	public boolean userSignup(User user) {
		int result = -1;
		
		try {
			Connection conn = ConnectorMysql.getConnection();
			result = DBConnector.userInsert(conn, user);
			conn.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (result > 0);
	}
	
	public boolean userUpdate(User user) {
		int result = -1;
		
		try {
			Connection conn = ConnectorMysql.getConnection();
			result = DBConnector.userUpdate(conn, user);
			conn.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (result > 0);
	}
	
	public boolean userRecovery(String email) {
		boolean retval = false;
		
		User user = null;
		try {
			Connection conn = ConnectorMysql.getConnection();
			user = DBConnector.userGetByEmail(conn, email);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if(user != null) {
			try {
				retval = comiot.core.email.EmailSender.sendRecovery(user);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		
		return retval;
	}
	
	public MqttConnectionConfiguration getConnection(int userPk) {
		MqttConnectionConfiguration mqttConfig = null;
		
		if(usersDeviceServer.containsKey(userPk)) {
			/* Refresh DB */
			try {
				Connection conn = ConnectorMysql.getConnection();
				mqttConfig = DBConnector.mqttConfigGetByUserPk(conn, userPk);
				conn.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return mqttConfig;
	}
	
	
	public boolean updateConnection(int userPk, MqttConnectionConfiguration mqttConfig) {
		int result = -1;
		
		if(usersDeviceServer.containsKey(userPk)) {
			/* Refresh DB */
			try {
				Connection conn = ConnectorMysql.getConnection();
				result = DBConnector.mqttConfigRefresh(conn, userPk, mqttConfig);
				if (result > 0) {
					mqttConfig = DBConnector.mqttConfigGetByUserPk(conn, userPk);
				}
				conn.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			/* Refresh connection */
			if (result > 0) {
				try {
					DeviceServer deviceServer = usersDeviceServer.get(userPk);
					deviceServer.disconnect();
					deviceServer.setAppConnection(new AppConnection(mqttConfig));
					deviceServer.connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return (result > 0);
	}
	
	
	public List<Device> deviceGetList(int userPk){
		List<Device> devices = null;
		
		if(usersDeviceServer.containsKey(userPk)) {
			devices = new ArrayList<>();
			for (Place place : usersDeviceServer.get(userPk).getPlaces()) {
				devices.addAll(place.getDevices());
			}
		}
		
		return devices;
	}
	
	public List<Place> placeGetList(int userPk){
		List<Place> places = null;
		
		if(usersDeviceServer.containsKey(userPk)) {
			places = usersDeviceServer.get(userPk).getPlaces();
		}
		
		return places;
	}
	
	public Device deviceGetByPk(int userPk, int devicePk){
		List<Place> places = placeGetList(userPk);
		Device device = null;
		
		for (Place place : places) {
			for (Device var : place.getDevices()) {
				if(var.getPk() == devicePk) {
					device = var;
				}
			}
		}
		
		return device;
	}
	
	public boolean deviceNew(int userPk, Device device){
		boolean retval = false;
		int result = -1;
		
		if(usersDeviceServer.containsKey(userPk)) {
			device.setPlace(1);					/* Hardcode until feature places is finish */ 
			
			/* Refresh DB */
			try {
				Connection conn = ConnectorMysql.getConnection();
				result = DBConnector.deviceInsert(conn, userPk, device);
				conn.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(result > 0) {
				DeviceServer deviceServer = usersDeviceServer.get(userPk);
				retval = deviceServer.addDevice(device);
			}
		}
		
		return ((result > 0) && retval);
	}
	
	public boolean deviceUpdate(int userPk, Device device){
		boolean retval = false;
		int result = -1;
		
		if(usersDeviceServer.containsKey(userPk)) {
			device.setPlace(1);					/* Hardcode until feature places is finish */
			
			/* Refresh DB */
			try {
				Connection conn = ConnectorMysql.getConnection();
				result = DBConnector.deviceUpdate(conn, userPk, device);
				conn.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(result > 0) {
				DeviceServer deviceServer = usersDeviceServer.get(userPk);
				retval = deviceServer.updateDevice(device);
			}
		}
		
		return ((result > 0) && retval);
	}
	
	public boolean deviceDeleteByPk(int userPk, int devicePk) {
		boolean retval = false;
		int result = -1;
		
		if(usersDeviceServer.containsKey(userPk)) {
			
			/* Refresh DB */
			try {
				Connection conn = ConnectorMysql.getConnection();
				result = DBConnector.deviceDelete(conn, devicePk);
				conn.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(result > 0) {
				DeviceServer deviceServer = usersDeviceServer.get(userPk);
				retval = deviceServer.removeDeviceByPk(userPk, devicePk);
			}
		}
		
		return ((result > 0) && retval);
	}
	
	public boolean deviceDelete(int userPk, Device device){
		boolean retval = false;
		int result = -1;
		
		if(usersDeviceServer.containsKey(userPk)) {
			
			/* Refresh DB */
			try {
				Connection conn = ConnectorMysql.getConnection();
				result = DBConnector.deviceDelete(conn, device.getPk());
				conn.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(result > 0) {
				DeviceServer deviceServer = usersDeviceServer.get(userPk);
				retval = deviceServer.removeDevice(device.getId(), device.getPlaceID());
			}
		}
		
		return ((result > 0) && retval);
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
	
	
	public boolean commandRequestSend(int userPk, DeviceCommandRequest command) {
		boolean retval = false;
		
		if(usersDeviceServer.containsKey(userPk)) {
			DeviceServer deviceServer = usersDeviceServer.get(userPk);
			retval = deviceServer.commandRequestSend(command);
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
