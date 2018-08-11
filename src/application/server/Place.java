package application;

import java.util.ArrayList;

import device.Device;
import device.IDeviceCommandsCallback;

public class Place {
	private int placeID;
	private String description;
	private ArrayList<Device> devices;
	private IDeviceCommandsCallback deviceCommandsCallback = null;
	
	public Place(int placeID) {
		this.placeID = placeID;
		devices = new ArrayList<>();
	}
	
	public void setCallbackRefreshState(IDeviceCommandsCallback deviceCommandsCallback) {
		this.deviceCommandsCallback = deviceCommandsCallback;
		
		for (Device device : devices) {
			device.setDeviceCommandsCallback(deviceCommandsCallback);
		}
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Device[] getDevices() {
		return devices.toArray(new Device[devices.size()]);
	}
	
	public Device getDevice(int deviceID) {
		int index = containsDeviceId(deviceID);
		return (index < 0) ? null : devices.get(index);
	}

	public int containsDeviceId(int deviceID) {
		int retval = -1;
		for (int index=0 ; index < devices.size() ; index++) {
			if(devices.get(index).getId() == deviceID) {
				retval = index;
				break;
			}
		}
		return retval;
	}
	
	public int getDeviceIdAvailable() {
		int retval = -1;
		for (int index=0 ; ((retval < 0) && (index < devices.size())) ; index++) {
			if(containsDeviceId(index) < 0) {
				retval = index;
				break;
			}
		}
		return retval;
	}
	
	public boolean addDevice(int deviceID) {
		boolean retval = false;
		
		if(containsDeviceId(deviceID) >= 0) {
			Device device = new Device(this.placeID, deviceID);
			device.setDeviceCommandsCallback(deviceCommandsCallback);
			devices.add(device);
			retval = true;
		}
		
		return retval;
	}
}
