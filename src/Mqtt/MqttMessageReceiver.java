package Mqtt;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttMessageReceiver implements MqttCallback{

	/**
	 * 
	 */
	public void connectionLost(Throwable cause) {
		System.out.println("connectionLost : " + cause);
	}

	/**
	 * 
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("Topic <" + topic + "> Message <" +  message.toString() + ">");
	}

	/**
	 * 
	 */
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("deliveryComplete : " + token);
	}

}
