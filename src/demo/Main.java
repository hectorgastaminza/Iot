package demo;

import java.util.Scanner;

import application.AppConnection;
import application.IStringCommandCallback;
import device.eDeviceStates;
import mqtt.MqttConnectionConfiguration;

public class Main implements IStringCommandCallback {

	public static void main(String[] args) {		
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		
		AppConnection connection = new AppConnection(new MqttConnectionConfiguration(), new Main());
		
		System.out.println("Hello comit...");
		System.out.println("This is a IOT test.");
		System.out.println("Enter values between [0 : 99]");
		
		connection.connect();
		
		while ((option <= 99) && (option >= 0)) {
			option = scanner.nextInt();
			connection.commandRefreshState(8, 16, eDeviceStates.ON, option);
		}
		
		System.out.println("Almost finish... waiting for disconnection...");
		
		System.out.println("Disconnection : " + (connection.disconnect() ? "OK" : "FAIL"));
		
		System.out.println("Bye!");
	}
	
	public boolean receivedStringCommand(String command) {
		System.out.println(command);
		return true;
	}
}
