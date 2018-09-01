package comiot.core.application.server;

import java.io.Serializable;

import comiot.core.database.DBRecord;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

public class User extends DBRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String email;
	private MqttConnectionConfiguration mqttConfig;
	
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public void setMqttConfig(MqttConnectionConfiguration mqttConfig) {
		this.mqttConfig = mqttConfig;
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public MqttConnectionConfiguration getMqttConfig() {
		return mqttConfig;
	}

}
