package comiot.core.application.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comiot.core.database.DBRecord;
import comiot.core.device.Device;
import comiot.core.device.command.DeviceCommandDispatcher;
import comiot.core.device.command.DeviceCommandRefreshState;

public class Place extends DBRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private int placeID;
	private String placeName;
	private String description;
	private HashMap<Integer, Device> devices;
	
	public Place(int placeID) {
		this.placeID = placeID;
		devices = new HashMap<>();
	}
	
	public int getPlaceID() {
		return placeID;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Device> getDevices() {
		return (new ArrayList<Device>(devices.values()));
	}
	
	public Device getDevice(int deviceID) {
		Device device = null;
		
		if(devices.containsKey(deviceID)) {
			device = devices.get(deviceID);
		}
		
		return device;
	}

	public boolean containsDeviceId(int deviceID) {
		return devices.containsKey(deviceID);
	}
	
	public int getDeviceIdAvailable() {
		int retval = -1;
		
		for (int idx = Device.DEVICE_ID_MIN; idx < Device.DEVICE_ID_MAX; idx++) {
			if(!containsDeviceId(idx)) {
				retval = idx;
				break;
			}
		}
		
		return retval;
	}
	
	public boolean addDevice(Device device) {
		boolean retval = false;
		
		if(!containsDeviceId(device.getId())) {
			devices.put(device.getId(), device);
			retval = true;
		}
		
		return retval;
	}
	
	public boolean removeDevice(int deviceID) {
		boolean retval = false;
		
		if(containsDeviceId(deviceID)) {
			devices.remove(deviceID);
			retval = true;
		}
		
		return retval;
	}
	
	public boolean processDeviceCommandRefreshState(DeviceCommandRefreshState deviceCommandRefreshState) {
		boolean retval = false;
		
		int deviceID = deviceCommandRefreshState.getDeviceID();
		if(devices.containsKey(deviceID)) {
			Device device = devices.get(deviceID);
			retval = DeviceCommandDispatcher.processCommandRefresh(device, deviceCommandRefreshState);
		}
		
		return retval;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
}