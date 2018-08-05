package application;

import device.DeviceCommandRefreshState;
import device.DeviceCommandRequest;
import device.eDeviceCommands;
import device.eDeviceStates;
import mqtt.MqttConnection;

public class AppConnection implements mqtt.IMqttReceiveCallback, device.IDeviceCommandsCallback{
	private String mqttTopic = "comit";
	private mqtt.MqttConnectionConfiguration mqttConfig;
	private mqtt.MqttConnection mqttConnection;
	private IStringCommandCallback stringCommandCallback = null;

	public AppConnection(mqtt.MqttConnectionConfiguration mqttConfig) {
		this.mqttConfig = mqttConfig;
	}
	
	public AppConnection(mqtt.MqttConnectionConfiguration mqttConfig, IStringCommandCallback stringCommandCallback) {
		this(mqttConfig);
		this.stringCommandCallback = stringCommandCallback;
	}
	
	public void setStringCommandCallback(IStringCommandCallback stringCommandCallback) {
		this.stringCommandCallback = stringCommandCallback;
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
		if(stringCommandCallback != null) {
			stringCommandCallback.receivedStringCommand(data);
		}
	}

	@Override
	public boolean commandRefreshState(int placeID, int deviceID, eDeviceStates state, int value) {
		DeviceCommandRefreshState command = new DeviceCommandRefreshState(placeID, deviceID, state, value);
		return mqttConnection.MqttSend(command.toString());
	}

	@Override
	public boolean commandRequest(int placeID, int deviceID, eDeviceCommands commandID, int value) {
		DeviceCommandRequest command = new DeviceCommandRequest(placeID, deviceID, commandID, value);
		return mqttConnection.MqttSend(command.toString());
	}
}
