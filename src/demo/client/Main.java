package demo.client;

import application.client.DeviceClient;
import application.common.AppConnection;
import demo.eDemoValues;
import device.Device;
import protocol.mqtt.MqttConnectionConfiguration;

public class Main {

	public static void main(String[] args) {
		Device device = new Device(eDemoValues.PLACE_ID_GENERIC.getValue(), eDemoValues.DEVICE_ID_GENERIC.getValue());
		AppConnection appConnection = new AppConnection(new MqttConnectionConfiguration());
		
		System.out.println("Welcome to COMIOT");
		System.out.println("Starting DEMO client");
		
		DeviceClient.clientLaunch(device, appConnection);
		
		System.out.println("Closing application... almost done...");
		System.out.println("Bye!");
	}
}
