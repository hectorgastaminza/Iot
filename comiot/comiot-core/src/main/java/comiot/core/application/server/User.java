package comiot.core.application.server;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;

import comiot.core.database.DBRecord;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

public class User extends DBRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String email;
	private MqttConnectionConfiguration mqttConfig;
	
	/**
	 * Used by JSON constructor
	 */
	@JsonCreator
	public User() {
	}
	
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public MqttConnectionConfiguration getMqttConfig() {
		return mqttConfig;
	}
	public void setMqttConfig(MqttConnectionConfiguration mqttConfig) {
		this.mqttConfig = mqttConfig;
	}

}
