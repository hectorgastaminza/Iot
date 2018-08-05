package application;

import java.util.Scanner;

import device.eDeviceStates;
import mqtt.MqttConnectionConfiguration;

public class Main {

	public static void main(String[] args) {		
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		
		AppConnection connection = new AppConnection(new MqttConnectionConfiguration());
		
		System.out.println("Hello comit...");
		System.out.println("This is a IOT test.");
		System.out.println("Enter values between [0 : 99]");
		
		connection.connect();
		
		while ((option <= 99) && (option >= 0)) {
			option = scanner.nextInt();
			connection.refreshState(8, 16, eDeviceStates.ON, option);
		}
		
		System.out.println("Almost finish... waiting for disconnection...");
		
		System.out.println("Disconnection : " + (connection.disconnect() ? "OK" : "FAIL"));
		
		System.out.println("Bye!");
	}

}
