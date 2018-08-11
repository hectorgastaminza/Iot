package application.server;

import java.util.HashMap;

import application.common.AppConnection;
import application.common.IStringCommandCallback;
import device.Device;
import device.command.DeviceCommandRefreshState;

public class DeviceServer implements IStringCommandCallback {
	private AppConnection appConnection = null;
	private HashMap<Integer, Place> places = null;
	
	public DeviceServer(AppConnection appConnection) {
		this.appConnection = appConnection;
		this.appConnection.setStringCommandCallback(this);
		this.places = new HashMap<>();
	}
	
	public boolean addPlace(Place place) {
		boolean retval = false;
		
		if(!places.containsKey(place.getPlaceID())) {
			places.put(place.getPlaceID(), place);
			retval = true;
		}
		
		return retval;
	}
	
	public boolean removePlace(int placeID) {
		boolean retval = false;
		
		if(!places.containsKey(placeID)) {
			places.remove(placeID);
			retval = true;
		}
		
		return retval;
	}
	
	public boolean addDevice(Device device) {
		boolean retval = false;
		
		if(places.containsKey(device.getPlaceID())) {
			Place place = places.get(device.getPlaceID());
			retval = place.addDevice(device);
		}
		
		return retval;
	}

	public boolean removeDevice(int deviceID, int placeID) {
		boolean retval = false;
		
		if(places.containsKey(placeID)) {
			Place place = places.get(placeID);
			retval = place.removeDevice(placeID);
		}
		
		return retval;
	}

	@Override
	public boolean receivedStringCommand(String command) {
		boolean retval = false;
		
		DeviceCommandRefreshState[] commands = DeviceCommandRefreshState.createDeviceCommandRefreshState(command);
		
		if(commands.length > 0) {
			for (DeviceCommandRefreshState deviceCommandRefreshState : commands) {
				if(places.containsKey(deviceCommandRefreshState.getPlaceID())) {
					Place place = places.get(deviceCommandRefreshState.getPlaceID());
					place.processDeviceCommandRefreshState(deviceCommandRefreshState);
				}
			}
			retval = true;
		}
		
		return retval;
	}
	
	public boolean connect() {
		return appConnection.connect();
	}
	
	public boolean disconnect() {
		return appConnection.disconnect();
	}
	
	static public void serverLaunch(AppConnection appConnection) {
		boolean connection = false;
		application.server.DeviceServer deviceServer = new DeviceServer(appConnection);
		
		connection = deviceServer.connect();
		System.out.println("Connection : " + ((connection) ? "OK" : "FAIL"));
		if(connection) {
			
			//DeviceControl.console(device);
			
			System.out.println("Closing connection... almost done... waiting for disconnection...");
			connection = deviceServer.disconnect();
			System.out.println("Disconnection : " + ((connection) ? "OK" : "FAIL"));
		}
	}
}