package comiot.core.protocol.mqtt;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * 
 * @author hector
 *
 */
public class MqttListener {
	public MqttClient mqttClient;
	MqttConnectOptions connOpts;
	
	public MqttListener(String topic, MqttConnectionConfiguration configuration, MqttCallback callback)
	{
		mqttSuscribe(topic, configuration, callback);
	}
	
	private void mqttConnect(MqttConnectionConfiguration configuration) throws MqttException, MqttSecurityException {
		mqttClient = new MqttClient(configuration.getBrokerURL(), MqttClient.generateClientId(), new MemoryPersistence());
		connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(configuration.getCleanSession());
		connOpts.setUserName(configuration.getUsername());
		connOpts.setPassword(configuration.getPassword());
		
		mqttClient.connect(connOpts);
		System.out.println("Mqtt Connected");
	}
	
	public void setCallback(MqttCallback callback)
	{
		if(mqttClient != null)
		{
			mqttClient.setCallback(callback);
		}
	}
	
	/**
	 * Subscribe.
	 *
	 * @param topic
	 *            the topic
	 */
	private void mqttSuscribe(String topic, MqttConnectionConfiguration configuration, MqttCallback callback) {
		
		try {
			mqttConnect(configuration);

			setCallback(callback);
			
			mqttClient.subscribe(configuration.getRootTopic() + topic);
			System.out.println("Subscribed. Listening");
			
		} catch (MqttException me) {
			System.out.println("Mqtt reason " + me.getReasonCode());
			System.out.println("Mqtt msg " + me.getMessage());
			System.out.println("Mqtt loc " + me.getLocalizedMessage());
			System.out.println("Mqtt cause " + me.getCause());
			System.out.println("Mqtt excep " + me);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
		if((mqttClient != null) && (mqttClient.isConnected())) {
			mqttClient.disconnect();
		}
	}
}
