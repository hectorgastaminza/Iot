package comiot.backend;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import comiot.core.application.common.AppConnection;
import comiot.core.application.server.DeviceServer;
import comiot.core.application.server.Place;
import comiot.core.database.DBConnector;
import comiot.core.database.mysql.ConnectorMysql;
import comiot.core.device.Device;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

public class UserModelLoadThread implements Runnable {

	private DeviceServer deviceServer;
	private int userPk;
	
	public UserModelLoadThread(DeviceServer deviceServer, int userPk) {
		this.deviceServer = deviceServer;
		this.userPk = userPk;
	}
	
	@Override
	public void run() {
		loadPlaces(deviceServer, userPk);
		loadDevices(deviceServer, userPk);
		loadConnection(deviceServer, userPk);
		
		try {
			deviceServer.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean loadPlaces(DeviceServer deviceServer, int userPk) {
		boolean retval = false;

		List<Place> places = null;

		Connection conn = ConnectorMysql.getConnection();

		try {
			places = DBConnector.placesGetByUserPk(conn, userPk);
			for (Place place : places) {
				deviceServer.addPlace(place);
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

		return retval;
	}

	public boolean loadDevices(DeviceServer deviceServer, int userPk) {
		boolean retval = false;

		List<Device> devices = null;

		Connection conn = ConnectorMysql.getConnection();

		try {
			devices = DBConnector.devicesGetByUserPk(conn, userPk);
			for (Device device : devices) {
				deviceServer.addDevice(device);
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

		return retval;
	}

	public boolean loadConnection(DeviceServer deviceServer, int userPk) {
		boolean retval = false;

		MqttConnectionConfiguration mqttConfig = null;

		Connection conn = ConnectorMysql.getConnection();

		try {
			mqttConfig = DBConnector.mqttConfigGetByUserPk(conn, userPk);
			deviceServer.disconnect();
			deviceServer.setAppConnection(new AppConnection(mqttConfig));
			retval = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if(!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retval;
	}

}
