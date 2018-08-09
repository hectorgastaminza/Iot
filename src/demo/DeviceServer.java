package demo;

import java.util.Scanner;

import application.AppConnection;
import application.IStringCommandCallback;
import device.DeviceCommandRefreshState;
import device.eDeviceCommands;
import mqtt.MqttConnectionConfiguration;

public class DeviceServer implements IStringCommandCallback {

	private int placeID;
	private int deviceID;
	private int option = -1;
	
	public DeviceServer(int placeID, int deviceID) {
		this.placeID = placeID;
		this.deviceID = deviceID;
	}
	
	public boolean receivedStringCommand(String command) {
		boolean retval = false;
		
		DeviceCommandRefreshState[] commands = DeviceCommandRefreshState.createDeviceCommandRefreshState(command);
		
		if(commands.length > 0) {
			for (DeviceCommandRefreshState deviceCommandRefreshState : commands) {
				System.out.println("Device: " + deviceCommandRefreshState.getDeviceID() + 
						" | Place: " + deviceCommandRefreshState.getPlaceID() +
						" | State: " + deviceCommandRefreshState.getState().toString()
						);
			}
			retval = true;
		}
		
		return retval;
	}
	
	public void createDeviceServer() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Server " + deviceID + " on place " + placeID + " connecting...");

		AppConnection connection = new AppConnection(new MqttConnectionConfiguration(), this);
		boolean result =  connection.connect();
		
		System.out.println("Connection : " + result);		
		
		while (option != 0) {
			System.out.println("1 - Device on");
			System.out.println("2 - Device off");
			System.out.println("0 - exit");
			System.out.println("Enter option selected:");
			option = scanner.nextInt();
			
			switch (option) {
			case 1:
				connection.commandRequest(placeID, deviceID, eDeviceCommands.ON, 1);
				break;
			case 2:
				connection.commandRequest(placeID, deviceID, eDeviceCommands.OFF, 2);
				break;
			default:
				break;
			}
		}
				
		System.out.println("Closing device server... almost done... waiting for disconnection...");
		
		//scanner.close();
		
		System.out.println("Disconnection : " + (connection.disconnect() ? "OK" : "FAIL"));
		
		System.out.println("Bye!");		
	}
	
}
