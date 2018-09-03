package comiot.backend.controller;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comiot.core.database.DBConnector;
import comiot.core.database.mysql.ConnectorMysql;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

@RestController
public class ConnectionController {
	
	@RequestMapping("/connection")
	public boolean getUserLogin(@RequestParam(value="userpk", defaultValue="-1") int userpk, 
			@RequestParam(value="mqtthost", defaultValue="") String mqtthost,
			@RequestParam(value="mqttport", defaultValue="0") int mqttport,
			@RequestParam(value="mqttusername", defaultValue="") String mqttusername,
			@RequestParam(value="mqttpassword", defaultValue="") String mqttpassword,
			@RequestParam(value="mqtttopic", defaultValue="") String mqtttopic) {
		return updateConnection(userpk, mqtthost, mqttport, mqttusername, mqttpassword, mqtttopic);
	}
	
	private boolean updateConnection(int userPk, String mqtthost, int mqttport, String mqttusername, String mqttpassword, String mqtttopic) {
		boolean retval = false;
		
		MqttConnectionConfiguration mqttConfig = new MqttConnectionConfiguration(mqtthost, 
				mqttport, 
				mqttusername, 
				mqttpassword, 
				mqtttopic);
		
		int result = -1;
		try {
			Connection conn = ConnectorMysql.getConnection();
			result = DBConnector.mqttConfigRefresh(conn, userPk, mqttConfig);
			conn.close();				
		} catch (SQLException e) {
			e.printStackTrace();
		}

		retval = (result > 0);
		
		return retval;
	}
}