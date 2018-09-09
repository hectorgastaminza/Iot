package comiot.core.device;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import comiot.core.application.server.Place;
import comiot.core.device.command.DeviceCommandDispatcher;
import comiot.core.device.command.eDeviceCommands;

public class DeviceControl {
	
	public static void console(Device device) {
		Scanner scanner = new Scanner(System.in);
    	KeyReadNonBlocking keyReader = new KeyReadNonBlocking(scanner);
        ExecutorService executor = Executors.newCachedThreadPool();

		int option = -1;
		int exit = 0;
		eDeviceCommands command = eDeviceCommands.NONE;
		eDeviceStates previousState =  device.getState();
		device.setDebugMessages(true);
		
		showMenu(exit, command);
		showDeviceStatus(device);
		executor.submit(keyReader);
		
		while (option != exit) {
			
			if(device.getState() != previousState) {
				previousState = device.getState();
				showDeviceStatus(device);
			}
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(keyReader.isNewKey()) {
				try {
					option = Integer.parseInt(keyReader.getKey());
					
					if(option != exit)
					{
						command = eDeviceCommands.getFromValue(option);
						if(command != eDeviceCommands.NONE) {
							int value = enterValue(command, keyReader);
							DeviceCommandDispatcher.processCommand(device, command, value);
						}
						command = eDeviceCommands.NONE;
						showMenu(exit, command);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
		executor.shutdown();
		scanner.close();
	}

	private static void showMenu(int exit, eDeviceCommands command) {
		StringBuilder strLine = new StringBuilder();
		addStringLine(strLine, '*', 50);
		String format = "%4s - %s%n";
		/* Auto-generating menu */
		System.out.println("\n" + strLine);
		System.out.println("MENU");
		System.out.println(strLine);
		/* List of commands */
		for (eDeviceCommands var : eDeviceCommands.values()) {
			if((var.getValue() != exit) && (var != eDeviceCommands.RESET))
			{
				System.out.printf(format, var.getValue(), var.getName());
			}
		}
		/* Exit command */
		System.out.printf(format, exit, "exit");
		System.out.println(strLine);
		System.out.println("Enter option selected:");
	}

	static private int enterValue(eDeviceCommands command, KeyReadNonBlocking keyReader) {
		int retval = 0;

		if(command == eDeviceCommands.SET_VALUE) {
			retval = -1;
			System.out.println("\nPlease enter a value: ");
			
			while(retval < 0) {
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(keyReader.isNewKey()) {
					try {
						retval = Integer.parseInt(keyReader.getKey());
					}
					catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}

		return retval;
	}
	
	static public void showDeviceStatus(Device device) {
		StringBuilder stringbuilder = new StringBuilder();
		addStringLine(stringbuilder, '*', 50);
		String format = "%-15s%-15s%s%n";

		System.out.println("\n" + stringbuilder);
		System.out.println("DEVICE " + device.getId() + " | PLACE " + device.getPlaceID() +" | STATUS");
		System.out.println(stringbuilder);
		System.out.printf(format, "State", ":", device.getState().toString());
		System.out.printf(format, "Value", ":", device.getValue());
		System.out.println(stringbuilder);
	}
	
	static public void showPlaceStatus(Place place) {
		System.out.println("PLACE : " + place.getPlaceID() + " | " + place.getDescription() + " |");
		for (Device device : place.getDevices()) {
			showDeviceStatus(device);
		}
	}
	
	static private void addStringLine(StringBuilder stringbuilder, char c, int count) {
		for (int i = 0; i < count; i++) {
			stringbuilder.append(c);
		}
	}

}
