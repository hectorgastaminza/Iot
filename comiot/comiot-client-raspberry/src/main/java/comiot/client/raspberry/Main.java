package comiot.client.raspberry;

import java.util.Scanner;

import comiot.client.raspberry.device.DeviceRaspberryDS18B20;
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
		System.out.println("\nWelcome to COMIOT\n");

		Device device = selectDeviceRaspberry();

		if(device != null) {
			System.out.println("\nStarting RASPBERRY client");
			AppConnection appConnection = new AppConnection(new MqttConnectionConfiguration());
			DeviceClient.clientLaunch(device, appConnection);
		}

		System.out.println("\nClosing application... almost done...");
		System.out.println("Bye!");
	}
	
	
	static private Device selectDeviceRaspberry() {
		int placeId = eDemoValues.PLACE_ID_RASPBERRY.getValue();
		Device device = null;
		
		String msgSelectDevice = "Please select device: ";
		String msgGetDeviceId = "Please introduce device ID: ";
		
		System.out.println("Raspberry devices:");
		System.out.println("1 - Gpio (General purpose In/Out)");
		System.out.println("2 - DS18B20 Digital temperature sensor");
		System.out.println("0 - Exit");
		
		int selected = enterValue(msgSelectDevice, 0, 2);
		
		if(selected != 0) {
			int deviceId = enterValue(msgGetDeviceId, Device.DEVICE_ID_MIN, Device.DEVICE_ID_MAX);
			
			switch (selected) {
			case 1:
				String msgGetGpioPin = "Please introduce gpio pin number: ";
				int pinId = enterValue(msgGetGpioPin, DeviceRaspberryGpio.PIN_FIRST, DeviceRaspberryGpio.PIN_LAST);			
				device = new DeviceRaspberryGpio(placeId, deviceId, pinId);
				break;
			case 2:
				String msgPeriod = "Please introduce temperature reading frecuency in minutes (from 1 to 10): ";
				int period = enterValue(msgPeriod, DeviceRaspberryDS18B20.READ_TEMP_PERIOD_MIN, DeviceRaspberryDS18B20.READ_TEMP_PERIOD_MAX);			
				device = new DeviceRaspberryDS18B20(placeId, deviceId, period);
				break;
			default:
				break;
			}
		}
		
		return device;
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
