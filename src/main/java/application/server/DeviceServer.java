package application.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.common.AppConnection;
import application.common.IStringCommandCallback;
import device.Device;
import device.DeviceControl;
import device.command.DeviceCommandRefreshState;

public class DeviceServer implements IStringCommandCallback {
	private AppConnection appConnection = null;
	private HashMap<Integer, Place> places = null;
	
	public DeviceServer() {
		this.places = new HashMap<>();
	}
	
	public DeviceServer(AppConnection appConnection) {
		this();
		setAppConnection(appConnection);
	}
	
	public void setAppConnection(AppConnection appConnection) {
		this.appConnection = appConnection;
		this.appConnection.setStringCommandCallback(this);
	}
	
	public List<Place> getPlaces(){
		return (new ArrayList<Place>(places.values()));
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
					retval = place.processDeviceCommandRefreshState(deviceCommandRefreshState);
				
					if(retval) {
						// Refresh UI
					}
				}
			}
		}
		
		return retval;
	}
	
	public boolean connect() {
		return appConnection.connect();
	}
	
	public boolean disconnect() {
		return appConnection.disconnect();
	}
	
	public void serverLaunch() {
		boolean connection = false;
		
		connection = connect();
		System.out.println("Connection : " + ((connection) ? "OK" : "FAIL"));
		if(connection) {
			
			for (Place place : places.values()) {
				DeviceControl.showPlaceStatus(place);
			}
			
			System.out.println("Closing connection... almost done... waiting for disconnection...");
			connection = disconnect();
			System.out.println("Disconnection : " + ((connection) ? "OK" : "FAIL"));
		}
	}
}
