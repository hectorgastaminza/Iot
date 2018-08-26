package demo.server;

import application.common.AppConnection;
import application.server.DeviceServer;
import application.server.Place;
import demo.eDemoValues;
import device.Device;
import protocol.mqtt.MqttConnectionConfiguration;

public class Main {

	public static void main(String[] args) {
		System.out.println("Welcome to COMIOT");
		System.out.println("Starting DEMO server");
		/* Load configuration */
		MqttConnectionConfiguration mqttConfig = new MqttConnectionConfiguration();
		AppConnection appConnection = new AppConnection(mqttConfig );
		DeviceServer deviceServer = new DeviceServer(appConnection);
		/* Load places */
		Place place = new Place(eDemoValues.PLACE_ID_GENERIC.getValue());
		place.setDescription("Generic place");
		deviceServer.addPlace(place);
		place = new Place(eDemoValues.PLACE_ID_RASPBERRY.getValue());
		place.setDescription("Raspberry place");
		deviceServer.addPlace(place);		
		/* Load devices */
		Device device = new Device(eDemoValues.PLACE_ID_GENERIC.getValue(), eDemoValues.DEVICE_ID_GENERIC.getValue());
		deviceServer.addDevice(device);
		device = new Device(eDemoValues.PLACE_ID_GENERIC.getValue(), 99);
		deviceServer.addDevice(device);
		device = new Device(eDemoValues.PLACE_ID_GENERIC.getValue(), 100);
		deviceServer.addDevice(device);
		device = new Device(eDemoValues.PLACE_ID_RASPBERRY.getValue(), eDemoValues.DEVICE_ID_RASPBERRY.getValue());
		deviceServer.addDevice(device);
		/* Launch */
		deviceServer.serverLaunch();
	}
}
