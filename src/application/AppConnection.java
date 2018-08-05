package application;

import device.DeviceCommandRefreshState;
import device.eDeviceStates;
import mqtt.MqttConnection;

public class AppConnection implements mqtt.IMqttReceiveCallback, device.IDeviceRefreshState{
	private String mqttTopic = "comit";
	private mqtt.MqttConnectionConfiguration mqttConfig;
	private mqtt.MqttConnection mqttConnection;

	public AppConnection(mqtt.MqttConnectionConfiguration mqttConfig) {
		this.mqttConfig = mqttConfig;
	}
	
	public boolean connect() {
		if(mqttConnection == null) {
			mqttConnection = new MqttConnection(mqttConfig, this);
			mqttConnection.mqttTopicSet(mqttTopic);
		}
		
	    return mqttConnection.connect();
	}
	
	public boolean disconnect() {
		return mqttConnection.disconnect();
	}

	@Override
	public void mqttReceive(String data) {
		System.out.println(data);
	}

	@Override
	public boolean refreshState(int placeID, int deviceID, eDeviceStates state, int value) {
		DeviceCommandRefreshState command = new DeviceCommandRefreshState(placeID, deviceID, state, value);
		
		return mqttConnection.MqttSend(command.toString());
	}
}
