package device;

import java.util.Scanner;

import application.server.Place;
import device.command.DeviceCommandDispatcher;
import device.command.eDeviceCommands;

public class DeviceControl {
	
	public static void console(Device device) {
		Scanner scanner = new Scanner(System.in);
		int option = -1;
		int exit = 0;
		eDeviceCommands command = eDeviceCommands.NONE;

		while (option != exit) {
			command = eDeviceCommands.NONE;
			showDeviceStatus(device);
			showMenu(exit, command);
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

	static private int enterValue(eDeviceCommands command) {
		int retval = 0;

		if(command == eDeviceCommands.SET_VALUE) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nPlease enter a value: ");
			retval = scanner.nextInt();
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
