package protocol.mqtt;

public class MqttConnectionConfiguration {
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
		this.brokerHost = brokerHost;
		this.brokerPort = brokerPort;
		this.brokerWebSocketsPort = brokerWebSocketsPort;
		this.userId = userId;
		this.password = password;
		this.rootTopic = rootTopic;
	}
	
	protected String getUsername() {
		return userId;
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
