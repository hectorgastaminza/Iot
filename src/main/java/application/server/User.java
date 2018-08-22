package application.server;

import database.DBRecord;
import protocol.mqtt.MqttConnectionConfiguration;

public class User extends DBRecord {
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
