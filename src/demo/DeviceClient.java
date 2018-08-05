package demo;

import java.util.Scanner;

import application.AppConnection;
import application.IStringCommandCallback;
import device.Device;
import device.DeviceCommandDispatcher;
import device.DeviceCommandRequest;
import device.eDeviceStates;
import mqtt.MqttConnectionConfiguration;

public class DeviceClient implements IStringCommandCallback {
	private int placeID;
	private int deviceID;
	private int option = -1;
	private Device device;
	
	public DeviceClient(int placeID, int deviceID) {
		this.placeID = placeID;
		this.deviceID = deviceID;
		device = new Device(placeID, deviceID);
	}
	
	public boolean receivedStringCommand(String command) {
		boolean retval = false;
		
		DeviceCommandRequest[] commands = DeviceCommandRequest.createDeviceCommandRequest(command);
		
		if(commands.length > 0) {
			for (DeviceCommandRequest deviceCommandRequest : commands) {
				DeviceCommandDispatcher.processCommandRequest(device, deviceCommandRequest);
			}
			retval = true;
		}
		
		return retval;
	}
	
	public void createDeviceClient() {
		Scanner scanner = new Scanner(System.in);
		
		AppConnection connection = new AppConnection(new MqttConnectionConfiguration(), this);
		device.setDeviceCommandsCallback(connection);
		
		System.out.println("Device " + deviceID + " on place " + placeID + " connecting...");
		System.out.println("Connection : " + connection.connect());		
		
		while (option != 0) {
			System.out.println("1 - Device on");
			System.out.println("2 - Device off");
			System.out.println("0 - exit");
			System.out.println("Enter option selected:");
			option = scanner.nextInt();
			
			switch (option) {
			case 1:
				device.on();
				break;
			case 2:
				device.off();
				break;
			default:
				break;
			}
		}
		
		System.out.println("Closing device client... almost done... waiting for disconnection...");

		//scanner.close();
		
		System.out.println("Disconnection : " + (connection.disconnect() ? "OK" : "FAIL"));
		
		System.out.println("Bye!");		
	}
}
