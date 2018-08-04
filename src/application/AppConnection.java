package application;

import device.DeviceCommand;
import device.eDeviceStates;
import mqtt.MqttConnection;
import mqtt.MqttPublisher;

public class AppConnection implements mqtt.IMqttReceiveCallback, device.IDeviceRefreshState{
	protected String mqttTopic = "comit";
	protected mqtt.MqttConnectionConfiguration mqttConfig;
	protected mqtt.MqttConnection mqttConnection;

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

	@Override
	public void mqttReceive(String data) {
	}

	@Override
	public boolean refreshState(int place, int id, eDeviceStates state, int value) {
		boolean retval = false;
		
		//DeviceCommand command = new DeviceCommand(place, id, state, value);
		
		//this.mqttConnection.MqttSend(command.toString());
		
		return retval;
	}
}
