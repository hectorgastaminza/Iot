package application.client;

import java.util.Scanner;

import application.AppConnection;
import application.IStringCommandCallback;
import device.Device;
import device.DeviceCommandDispatcher;
import device.DeviceCommandRequest;
import device.eDeviceCommands;
import mqtt.MqttConnectionConfiguration;

public class DeviceClient implements IStringCommandCallback {
	private AppConnection appConnection = null;
	private Device device = null;
	
	public DeviceClient(AppConnection appConnection, Device device) {
		this.appConnection = appConnection;
		this.device = device;
	}

	@Override
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
	
	public boolean connect() {
		boolean retval = false;
		
		retval = appConnection.connect();
		device.setDeviceCommandsCallback(appConnection);
		
		return retval;
	}
	
	public boolean controlDevice(boolean autoconnect, boolean showConnectionMessages) {
		boolean retval = true;

		if(autoconnect) {
			retval = connect();
			System.out.println("Connection : " + ((retval) ? "OK" : "FAIL"));
		}

		if(retval == true) {
			Scanner scanner = new Scanner(System.in);
			int option = -1;
			int exit = 0;
			eDeviceCommands command = eDeviceCommands.NONE;

			while (option != exit) {
				command = eDeviceCommands.NONE;
				/* Auto-generating menu */
				System.out.println("Menu");
				/* List of commands */
				for (eDeviceCommands var : eDeviceCommands.values()) {
					if(var.getValue() != exit)
					{
						System.out.println(var.getValue() + " - " + var.getName());
					}
				}
				/* Exit command */
				System.out.println("0 - exit");
				System.out.println("Enter option selected:");
				option = scanner.nextInt();
				
				if(option != exit)
				{
					command = eDeviceCommands.getFromValue(option);
					if(command != eDeviceCommands.NONE) {
						int value = enterValue(command);
						DeviceCommandDispatcher.processCommand(device, command, value);
					}
				}
			}
			
			if(autoconnect) {
				System.out.println("Closing connection... almost done... waiting for disconnection...");
				retval = appConnection.disconnect();
				System.out.println("Disconnection : " + ((retval) ? "OK" : "FAIL"));
			}
		}
		
		return retval;
	}
	
	private int enterValue(eDeviceCommands command) {
		int retval = 0;
		
		if(command == eDeviceCommands.SET_VALUE) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter a value: ");
			retval = scanner.nextInt();
		}
		
		return retval;
	}
}
