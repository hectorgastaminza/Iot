package comiot.client.virtual;

import comiot.core.application.client.DeviceClient;
import comiot.core.application.common.AppConnection;
import comiot.core.device.Device;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

public class Main {

	public static void main(String[] args) {
		int placeId = eDemoValues.PLACE_ID_GENERIC.getValue();
		int deviceId = eDemoValues.DEVICE_ID_GENERIC.getValue();
		
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
		
		Device device = new Device(placeId, deviceId);
		AppConnection appConnection = new AppConnection(new MqttConnectionConfiguration());
		
		System.out.println("Welcome to COMIOT");
		System.out.println("Starting DEMO client");
		
		DeviceClient.clientLaunch(device, appConnection);
		
		System.out.println("Closing application... almost done...");
		System.out.println("Bye!");
	}
}
