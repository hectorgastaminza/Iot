package demo;

import java.util.Scanner;

import application.AppConnection;
import application.client.DeviceClient;
import device.Device;
import device.DeviceControl;
import mqtt.MqttConnectionConfiguration;

public class Main {

	/**
	 * See
	 * https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html
	 * @param args
	 */
	
	public static void main(String[] args) {		
		final int PLACE_ID = 8;
		final int DEVICE_ID = 9;
		Scanner scanner = new Scanner(System.in);
		
		int option = -1;
		
		System.out.println("Hello comit...");
		System.out.println("This is a IOT test.");
		
		while (option != 0) {
			System.out.println("1 - Device client");
			System.out.println("2 - Device server");
			System.out.println("0 - exit");
			System.out.println("Enter option selected:");
			option = scanner.nextInt();
			
			switch (option) {
			case 1:
				{
					Device device = new Device(PLACE_ID, DEVICE_ID);
					AppConnection appConnection = new AppConnection(new MqttConnectionConfiguration());
					
					DeviceClient.clientLaunch(device, appConnection);
				}
				break;
			case 2:
				{
					DeviceServer server = new DeviceServer(PLACE_ID, DEVICE_ID);
					server.createDeviceServer();
				}
				break;
			default:
				break;
			}
		}
		
		System.out.println("Closing application... almost done...");

		scanner.close();
		
		System.out.println("Bye!");
	}
	

}
