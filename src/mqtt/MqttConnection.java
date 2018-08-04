package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttConnection implements MqttCallback {
	private MqttConnectionConfiguration mqttConfig;
	private MqttListener mqttListener;
	private MqttPublisher mqttPublisher;
	private IMqttReceiveCallback mqttReceiveCallback;
	protected String mqttTopic = "carp";

	public MqttConnection(MqttConnectionConfiguration mqttConfig, IMqttReceiveCallback mqttReceiveCallback) {
		this(mqttConfig);
		this.mqttReceiveCallback = mqttReceiveCallback;
	}

	public MqttConnection(MqttConnectionConfiguration mqttConfig) {
		this.mqttConfig = mqttConfig;
	}
	
	public void mqttReceiveCallbackSet(IMqttReceiveCallback mqttReceiveCallback) {
		this.mqttReceiveCallback = mqttReceiveCallback;
	}

	public void mqttTopicSet(String mqttTopic) {
		this.mqttTopic = mqttTopic;
	}
	
	public boolean connect() {
		mqttListener = new MqttListener(mqttTopic, mqttConfig, this);
		mqttPublisher = new MqttPublisher(mqttConfig);
		return true;
	}
	
	public boolean MqttSend(String data) {
		return this.mqttPublisher.publish(mqttTopic, data);
	}

	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		if(mqttReceiveCallback != null) {
			if(mqttTopic.contentEquals(arg0)) {
				mqttReceiveCallback.mqttReceive(arg1.toString());
			}
		}
	}
}
