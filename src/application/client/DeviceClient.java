package application.client;

import application.AppConnection;
import application.IStringCommandCallback;
import device.Device;
import device.DeviceCommandDispatcher;
import device.DeviceCommandRequest;
import device.DeviceControl;

public class DeviceClient implements IStringCommandCallback {
	private AppConnection appConnection = null;
	private Device device = null;
	
	public DeviceClient(AppConnection appConnection, Device device) {
		this.appConnection = appConnection;
		this.appConnection.setStringCommandCallback(this);
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
		
	static public void clientLaunch(Device device, AppConnection appConnection) {
		boolean connection = false;
		application.client.DeviceClient deviceClient = new DeviceClient(appConnection, device);
		
		connection = deviceClient.connect();
		System.out.println("Connection : " + ((connection) ? "OK" : "FAIL"));
		if(connection) {
			
			DeviceControl.console(device);
			
			System.out.println("Closing connection... almost done... waiting for disconnection...");
			connection = appConnection.disconnect();
			System.out.println("Disconnection : " + ((connection) ? "OK" : "FAIL"));
		}
	}
}
