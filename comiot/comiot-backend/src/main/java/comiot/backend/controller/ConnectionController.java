package comiot.backend.controller;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comiot.backend.UserModel;
import comiot.core.database.DBConnector;
import comiot.core.database.mysql.ConnectorMysql;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

@RestController
public class ConnectionController {
	
	@Autowired
	public UserModel userModel;
	
	@RequestMapping("/connection/update")
	public boolean getUpdateConnection(@RequestParam(value="userpk", defaultValue="-1") int userpk, 
			@RequestParam(value="mqtthost", defaultValue="") String mqtthost,
			@RequestParam(value="mqttport", defaultValue="0") int mqttport,
			@RequestParam(value="mqttusername", defaultValue="") String mqttusername,
			@RequestParam(value="mqttpassword", defaultValue="") String mqttpassword,
			@RequestParam(value="mqtttopic", defaultValue="") String mqtttopic) {
		return userModel.updateConnection(userpk, mqtthost, mqttport, mqttusername, mqttpassword, mqtttopic);
	}
	
}