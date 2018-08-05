package application;

import java.util.Scanner;

import device.eDeviceStates;
import mqtt.MqttConnectionConfiguration;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello comit...");
		Scanner scanner = new Scanner(System.in);
		int option = -1;
		
		AppConnection connection = new AppConnection(new MqttConnectionConfiguration());
		connection.connect();
		
		while (option < 99) {
			option = scanner.nextInt();
			connection.refreshState(8, 16, eDeviceStates.ON, option);
		}
		
		System.out.println("Thanks! :" + connection.disconnect());
	}

}
