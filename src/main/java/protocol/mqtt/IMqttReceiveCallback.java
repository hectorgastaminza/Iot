package protocol.mqtt;

public interface IMqttReceiveCallback {
	public void mqttReceive(String data);
}