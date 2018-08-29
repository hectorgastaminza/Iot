package protocol.mqtt;

import java.io.Serializable;

import database.DBRecord;

public class MqttConnectionConfiguration extends DBRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String brokerHost = "mqtt.dioty.co";
	private int brokerPort = 1883;
	/* Optional */
	private int brokerWebSocketsPort = 8080;
	/* Optional */
	private String userId = "comiotproject@gmail.com";
	/* Optional */
	private String password = "fbe4629f";
	/* Optional */
	private String rootTopic = "/comiotproject@gmail.com/";
	/* Quality of service */
	private int qos = 0;
	/* Clean session */
	private boolean cleanSession = true;

	/** Generic constructor */
	public MqttConnectionConfiguration() {}
	
	public MqttConnectionConfiguration(
	String brokerHost, int brokerPort, int brokerWebSocketsPort,
	String userId, String password,
	String rootTopic
	) {
		this(brokerHost, brokerPort, userId, password, rootTopic);
		this.brokerWebSocketsPort = brokerWebSocketsPort;
	}
	
	public MqttConnectionConfiguration(
	String brokerHost, int brokerPort,
	String userId, String password,
	String rootTopic
	) {
		this.brokerHost = brokerHost;
		this.brokerPort = brokerPort;
		this.userId = userId;
		this.password = password;
		this.rootTopic = rootTopic;
	}
	
	public String getBrokerHost() {
		return brokerHost;
	}
	
	public int getBrokerPort() {
		return brokerPort;
	}
	
	public String getUsername() {
		return userId;
	}
	
	public String getPasswordStr() {
		return password;
	}

	protected char[] getPassword() {
		return password.toCharArray();
	}

	protected String getBrokerURL() {
		return ("tcp://"+brokerHost+":"+String.valueOf(brokerPort));		
	}
	
	protected int getBrokerWebSocketsPort() {
		return brokerWebSocketsPort;		
	}
	
	public String getRootTopic() {
		return rootTopic;
	}
	
	public int getQOS() {
		return qos;
	}
	
	public boolean getCleanSession() {
		return cleanSession;
	}
}
