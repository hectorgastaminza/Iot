package demo.client;

import application.client.DeviceClient;
import application.common.AppConnection;
import device.Device;
import protocol.mqtt.MqttConnectionConfiguration;

public class Main {

	public static void main(String[] args) {
		final int PLACE_ID = 8;
		final int DEVICE_ID = 5;
		
		Device device = new Device(PLACE_ID, DEVICE_ID);
		AppConnection appConnection = new AppConnection(new MqttConnectionConfiguration());
		
		System.out.println("Welcome to COMIOT");
		System.out.println("Starting DEMO client");
		
		DeviceClient.clientLaunch(device, appConnection);
		
		System.out.println("Closing application... almost done...");
		System.out.println("Bye!");
	}
}
