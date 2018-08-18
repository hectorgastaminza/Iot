package demo.raspberry;

import application.client.DeviceClient;
import application.common.AppConnection;
import demo.eDemoValues;
import device.Device;
import device.raspberry.DeviceRaspberry;
import protocol.mqtt.MqttConnectionConfiguration;

public class Main {
	/**
	 * See
	 * https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html
	 * @param args
	 */
	
	public static void main(String[] args) {
		Device device = new DeviceRaspberry(eDemoValues.PLACE_ID_RASPBERRY.getValue(), eDemoValues.DEVICE_ID_RASPBERRY.getValue(), 0);
		AppConnection appConnection = new AppConnection(new MqttConnectionConfiguration());
		
		System.out.println("Welcome to COMIOT");
		System.out.println("Starting RASPBERRY client");
		
		DeviceClient.clientLaunch(device, appConnection);
		
		System.out.println("Closing application... almost done...");
		System.out.println("Bye!");
	}
}
