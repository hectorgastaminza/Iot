package comiot.core.application.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import comiot.core.application.common.AppConnection;
import comiot.core.application.common.IStringCommandCallback;
import comiot.core.device.Device;
import comiot.core.device.DeviceControl;
import comiot.core.device.command.DeviceCommandRefreshState;
import comiot.core.device.command.DeviceCommandRequest;

public class DeviceServer implements IStringCommandCallback {
	private AppConnection appConnection = null;
	private HashMap<Integer, Place> places = null;
	private int userId = -1; 
	private IDeviceStatusRefreshCallback deviceStatusRefreshCallback = null;
	
	public DeviceServer() {
		this.places = new HashMap<>();
	}
	
	public DeviceServer(AppConnection appConnection) {
		this();
		setAppConnection(appConnection);
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public IDeviceStatusRefreshCallback getDeviceStatusRefreshCallback() {
		return deviceStatusRefreshCallback;
	}

	public void setDeviceStatusRefreshCallback(IDeviceStatusRefreshCallback deviceStatusRefreshCallback) {
		this.deviceStatusRefreshCallback = deviceStatusRefreshCallback;
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
	
	public Place getPlace(int placeID) {
		Place retval = null;
		
		if(places.containsKey(placeID)) {
			retval = places.get(placeID);
		}
		
		return retval;
	}
	
	public boolean removePlace(int placeID) {
		boolean retval = false;
		
		if(places.containsKey(placeID)) {
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
	
	public boolean updateDevice(Device device) {
		boolean retval = false;
		
		if(places.containsKey(device.getPlaceID())) {
			Place place = places.get(device.getPlaceID());
			retval = place.updateDevice(device);
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
	
	
	public boolean removeDeviceByPk(int userPk, int devicePk) {
		boolean retval = false;

		for (Place place : places.values()) {
			if(place.removeDeviceByPk(devicePk)) {
				retval = true;
				break;
			}
		}
		
		return retval;
	}
	
	
	public boolean commandRequestSend(DeviceCommandRequest command) {
		boolean retval = false;
		
		if(appConnection != null) {
			retval = appConnection.commandRequest(command);
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
						System.out.println("Device: " + deviceCommandRefreshState.getDeviceID() + 
								" | Place: " + deviceCommandRefreshState.getPlaceID() +
								" | new state: " + deviceCommandRefreshState.getState()
								);
						//
						if(deviceStatusRefreshCallback != null) {
							deviceStatusRefreshCallback.deviceRefreshStatusCallback(userId, deviceCommandRefreshState);
						}
					}
				}
			}
		}
		
		return retval;
	}
	
	public boolean connect() {	
		return (appConnection != null) ? appConnection.connect() : false;
	}
	
	public boolean disconnect() {
		return (appConnection != null) ? appConnection.disconnect() : false;
	}
	
	public void serverLaunch() {
		boolean connection = false;
		
		connection = connect();
		System.out.println("Connection : " + ((connection) ? "OK" : "FAIL"));
		if(connection) {
			
			for (Place place : places.values()) {
				DeviceControl.showPlaceStatus(place);
			}
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("Press 0 to exit");
			int key = -1;
			while(key != 0) {
				key = scanner.nextInt();
			}
			
			System.out.println("Closing connection... almost done... waiting for disconnection...");
			scanner.close();
			connection = disconnect();
			System.out.println("Disconnection : " + ((connection) ? "OK" : "FAIL"));
		}
	}
}
