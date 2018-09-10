package comiot.core.protocol.mqtt;
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
public class MqttPublisher {

	MqttClient mqttClient;
	MqttConnectionConfiguration myConfiguration;
	MqttConnectOptions connOpts;
	
	public MqttPublisher(MqttConnectionConfiguration configuration)
	{
		try {
			
			mqttConnect(configuration);
			
		} catch (MqttException me) {
			System.out.println("Mqtt reason " + me.getReasonCode());
			System.out.println("Mqtt msg " + me.getMessage());
			System.out.println("Mqtt loc " + me.getLocalizedMessage());
			System.out.println("Mqtt cause " + me.getCause());
			System.out.println("Mqtt excep " + me);
		}
	}
	
	private void mqttConnect(MqttConnectionConfiguration configuration) throws MqttException, MqttSecurityException {
		myConfiguration = configuration;
		mqttClient = new MqttClient(configuration.getBrokerURL(), MqttClient.generateClientId(), new MemoryPersistence());
		connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(configuration.getCleanSession());
		connOpts.setUserName(configuration.getUsername());
		connOpts.setPassword(configuration.getPassword());
		mqttClient.connect(connOpts);
	}
	
	public boolean publish(String topic, String content) {
		boolean retval = false;
		
		try {
			if(mqttClient.isConnected())
			{
				mqttClient.publish(myConfiguration.getRootTopic() + topic, content.getBytes(), myConfiguration.getQOS(), true);
				retval = true;
			}
			else
			{
				System.out.println("Client is not connect");
			}
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
		
		return retval;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
		if((mqttClient != null) && (mqttClient.isConnected())) {
			mqttClient.disconnect();
		}
	}
}
