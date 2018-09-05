package comiot.backend;

import java.sql.Connection;
import java.sql.SQLException;
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
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

@Service
public class UserModel implements IDeviceStatusRefreshCallback {
	private HashMap<Integer, DeviceServer> usersDeviceServer = null;
	
	
	public UserModel() {
		usersDeviceServer = new HashMap<>();
	}
	
	
	public User userLogin(String username, String password) {
		User user = new User(username, "", "");
		
		int userPk = 0;
		try {
			Connection conn = ConnectorMysql.getConnection();
			userPk = DBConnector.userGetPk(conn, username, password);
			conn.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (userPk > 0) {
		    user.setPk(userPk);
		    
		    if(addUser(user.getPk())) {
		    	UserModelLoadThread runLoad = new UserModelLoadThread(usersDeviceServer.get(user.getPk()), user.getPk());
		        ExecutorService executor = Executors.newCachedThreadPool();
		        executor.submit(runLoad);
		    }
		}
		
		return user;
	}
	
	public boolean userSignup(String username, String password, String email) {
		int result = -1;
		
		User user = new User(username, password, email);
		
		try {
			Connection conn = ConnectorMysql.getConnection();
			result = DBConnector.userInsert(conn, user);
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
	
	
	public boolean updateConnection(int userPk, String mqtthost, int mqttport, String mqttusername, String mqttpassword, String mqtttopic) {
		int result = -1;
		
		if(usersDeviceServer.containsKey(userPk)) {
			MqttConnectionConfiguration mqttConfig = new MqttConnectionConfiguration(mqtthost, 
					mqttport, 
					mqttusername, 
					mqttpassword, 
					mqtttopic);
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
			Place place = usersDeviceServer.get(userPk).getPlace(1); /* Hardcode until feature places is lunched */
			if(place != null)
			{
				devices = place.getDevices(); 
			}
		}
		
		return devices;
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
			usersDeviceServer.get(1).getPlaces().get(1).getDevices();
			retval = usersDeviceServer.get(userPk).addDevice(device);
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
