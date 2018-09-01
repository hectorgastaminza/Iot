package comiot.client.raspberry;

import java.util.Scanner;

import com.pi4j.io.serial.RaspberryPiSerial;

import comiot.client.raspberry.device.DeviceRaspberryGpio;
import comiot.core.application.client.DeviceClient;
import comiot.core.application.common.AppConnection;
import comiot.core.device.Device;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

public class Main {
	/**
	 * See
	 * https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html
	 * @param args
	 */
	
	public static void main(String[] args) {
		final int placeId = eDemoValues.PLACE_ID_RASPBERRY.getValue();
		final String msgGetDeviceId = "Please introduce device ID: ";
		final String msgGetGpioPin = "Please gpio pin number: ";
		
		System.out.println("\nWelcome to COMIOT\n");
		
		int deviceId = enterValue(msgGetDeviceId, Device.DEVICE_ID_MIN, Device.DEVICE_ID_MAX);
		int pinId = enterValue(msgGetGpioPin, DeviceRaspberryGpio.PIN_FIRST, DeviceRaspberryGpio.PIN_LAST);

		System.out.println("\nStarting RASPBERRY client");

		Device device = new DeviceRaspberryGpio(placeId, deviceId, pinId);
		AppConnection appConnection = new AppConnection(new MqttConnectionConfiguration());
		
		DeviceClient.clientLaunch(device, appConnection);

		System.out.println("\nClosing application... almost done...");
		System.out.println("Bye!");
	}
	
	
	/* TODO: fix bug when scanner is closed */
	static private int enterValue(String msg, int min, int max) {
		int retval = -1;
		Scanner scanner = new Scanner(System.in);
		
		while ((retval < min) || (retval > max)) {
			System.out.println(msg);
			try {
				retval = scanner.nextInt();
			}
			catch (Exception e) {
				System.out.println("ERROR : Invalid parameters!");
			}
		}
		
		return retval;
	}

}
