package protocol.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttConnection implements MqttCallback {
	private MqttConnectionConfiguration mqttConfig;
	private MqttListener mqttListener;
	private MqttPublisher mqttPublisher;
	private IMqttReceiveCallback mqttReceiveCallback;
	protected String mqttTopic = "carp";
	protected String mqttFullPath;

	public MqttConnection(MqttConnectionConfiguration mqttConfig, IMqttReceiveCallback mqttReceiveCallback) {
		this(mqttConfig);
		this.mqttReceiveCallback = mqttReceiveCallback;
	}

	public MqttConnection(MqttConnectionConfiguration mqttConfig) {
		this.mqttConfig = mqttConfig;
		mqttFullPath = mqttConfig.getRootTopic() + mqttTopic;
	}
	
	public void mqttReceiveCallbackSet(IMqttReceiveCallback mqttReceiveCallback) {
		this.mqttReceiveCallback = mqttReceiveCallback;
	}

	public void mqttTopicSet(String mqttTopic) {
		this.mqttTopic = mqttTopic;
		mqttFullPath = mqttConfig.getRootTopic() + mqttTopic;
	}
	
	public boolean connect() {
		mqttListener = new MqttListener(mqttTopic, mqttConfig, this);
		return true;
	}
	
	public boolean disconnect() {
		boolean retval = true;
		try {
		if(mqttListener != null && mqttListener.mqttClient.isConnected())
		{
			mqttListener.mqttClient.disconnectForcibly();
			mqttListener.mqttClient.close();
			mqttListener.mqttClient = null;
		}
		if(mqttPublisher != null && mqttPublisher.mqttClient.isConnected())
		{
			mqttPublisher.mqttClient.disconnectForcibly();
			mqttPublisher.mqttClient.close();
			mqttPublisher.mqttClient = null;
		}
		}
		catch(MqttException e) { retval = false; }
		return retval;
	}
	
	public boolean MqttSend(String data) {
		if(mqttPublisher == null) {
			mqttPublisher = new MqttPublisher(mqttConfig);
		}
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
			if(mqttFullPath.contentEquals(arg0)) {
				mqttReceiveCallback.mqttReceive(arg1.toString());
			}
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.disconnect();
	}
}
