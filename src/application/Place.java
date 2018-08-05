package application;

import java.util.ArrayList;

import device.Device;
import device.IDeviceCommandsCallback;

public class Place {
	private int id;
	private String description;
	private ArrayList<Device> devices;
	private IDeviceCommandsCallback callbackRefreshState = null;
	
	public Place(int id) {
		this.id = id;
		devices = new ArrayList<>();
	}
	
	public void setCallbackRefreshState(IDeviceCommandsCallback callbackRefreshState) {
		this.callbackRefreshState = callbackRefreshState;
		
		for (Device device : devices) {
			device.setDeviceCommandsCallback(callbackRefreshState);
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
	
	public Device getDevice(int id) {
		int index = containsDeviceId(id);
		return (index < 0) ? null : devices.get(index);
	}

	public int containsDeviceId(int id) {
		int retval = -1;
		for (int index=0 ; index < devices.size() ; index++) {
			if(devices.get(index).getId() == id) {
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
	
	public boolean addDevice(int id) {
		boolean retval = false;
		
		if(containsDeviceId(id) >= 0) {
			devices.add(new Device(this.id, id));
			retval = true;
		}
		
		return retval;
	}
}
