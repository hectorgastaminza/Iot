package Device;

public class Device {
	private int id = 0;
	private String mqttTopic;
	
	public Device(int id, String mqttTopic) {
		this.id = id;
		this.mqttTopic = mqttTopic;
	}
	
	public boolean equals(Device device) {
		return (this.id == device.id) && (this.mqttTopic == device.mqttTopic);
	}
	
}
