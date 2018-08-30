package comiot.core.application.client;

import comiot.core.application.common.AppConnection;
import comiot.core.application.common.IStringCommandCallback;
import comiot.core.device.Device;
import comiot.core.device.DeviceControl;
import comiot.core.device.command.DeviceCommandDispatcher;
import comiot.core.device.command.DeviceCommandRequest;

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
				if((device.getId() == deviceCommandRequest.getDeviceID()) && (device.getPlaceID() == deviceCommandRequest.getPlaceID())){
					DeviceCommandDispatcher.processCommandRequest(device, deviceCommandRequest);
				}
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
	
	public boolean disconnect() {
		return appConnection.disconnect();
	}
		
	static public void clientLaunch(Device device, AppConnection appConnection) {
		boolean connection = false;
		comiot.core.application.client.DeviceClient deviceClient = new DeviceClient(appConnection, device);
		
		connection = deviceClient.connect();
		System.out.println("Connection : " + ((connection) ? "OK" : "FAIL"));
		if(connection) {
			
			DeviceControl.console(device);
			
			System.out.println("Closing connection... almost done... waiting for disconnection...");
			connection = deviceClient.disconnect();
			System.out.println("Disconnection : " + ((connection) ? "OK" : "FAIL"));
		}
	}
}
