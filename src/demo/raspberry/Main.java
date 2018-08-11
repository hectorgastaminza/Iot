package demo.raspberry;

import application.client.DeviceClient;
import application.common.AppConnection;
import device.Device;
import protocol.mqtt.MqttConnectionConfiguration;

public class Main {
	/**
	 * See
	 * https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html
	 * @param args
	 */
	
	public static void main(String[] args) {
		final int PLACE_ID = 8;
		final int DEVICE_ID = 9;
		
		Device device = new DeviceRaspberry(PLACE_ID, DEVICE_ID, 0);
		AppConnection appConnection = new AppConnection(new MqttConnectionConfiguration());
		
		System.out.println("Welcome to COMIOT");
		System.out.println("Starting RASPBERRY client");
		
		DeviceClient.clientLaunch(device, appConnection);
		
		System.out.println("Closing application... almost done...");
		System.out.println("Bye!");
	}
}
