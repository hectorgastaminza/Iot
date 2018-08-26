package demo.raspberry;

import application.client.DeviceClient;
import application.common.AppConnection;
import demo.eDemoValues;
import device.Device;
import device.raspberry.DeviceRaspberry;
import protocol.mqtt.MqttConnectionConfiguration;

public class Main {
	/**
	 * See
	 * https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html
	 * @param args
	 */
	
	public static void main(String[] args) {
		int placeId = eDemoValues.PLACE_ID_RASPBERRY.getValue();
		int deviceId = eDemoValues.DEVICE_ID_RASPBERRY.getValue();
		
		if(args.length > 0) {
			try {
				int auxDeviceId = Integer.parseInt(args[0]);
				if((auxDeviceId >= Device.DEVICE_ID_MIN) && (auxDeviceId <= Device.DEVICE_ID_MAX)){
					deviceId = auxDeviceId;
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println("ERROR : Invalid parameters!");
			}
		}
		
		Device device = new DeviceRaspberry(placeId, deviceId, 0);
		AppConnection appConnection = new AppConnection(new MqttConnectionConfiguration());
		
		System.out.println("Welcome to COMIOT");
		System.out.println("Starting RASPBERRY client");
		
		DeviceClient.clientLaunch(device, appConnection);
		
		System.out.println("Closing application... almost done...");
		System.out.println("Bye!");
	}
}
