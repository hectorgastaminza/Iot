package comiot.core.application.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import comiot.core.database.DBRecord;
import comiot.core.device.Device;
import comiot.core.device.command.DeviceCommandDispatcher;
import comiot.core.device.command.DeviceCommandRefreshState;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Place extends DBRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private int placeID;
	private String placeName;
	private String description;
	private List<Device> devices = new ArrayList<>();
	
	/**
	 * For JSON
	 */
	@JsonCreator
	public Place() {
	}
	
	public Place(int placeID) {
		this.placeID = placeID;
	}
	
	@JsonGetter("placeID")
	public int getPlaceID() {
		return placeID;
	}
	
	@JsonGetter("description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonGetter("devices")
	public List<Device> getDevices() {
		return  devices;
	}
	
	@JsonIgnore
	public Device getDevice(int deviceID) {
		Device device = null;
		
		for (Device var : devices) {
			if(var.getId() == deviceID) {
				device = var;
				break;
			}
		}
		
		return device;
	}
	
	@JsonIgnore
	public boolean containsDeviceId(int deviceID) {
		
		boolean retval = false;
		
		for (Device device : devices) {
			if(device.getId() == deviceID) {
				retval = true;
				break;
			}
		}
		
		return retval;
	}
	
	@JsonIgnore
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
	
	@JsonIgnore
	public boolean addDevice(Device device) {
		boolean retval = false;
		
		if(!containsDeviceId(device.getId())) {
			devices.add(device);
			retval = true;
		}
		
		return retval;
	}
	
	@JsonIgnore
	public boolean removeDevice(int deviceID) {
		boolean retval = false;

		for (Device var : devices) {
			if(var.getId() == deviceID) {
				devices.remove(var);
				retval = true;
				break;
			}
		}

		return retval;
	}
	
	@JsonIgnore
	public boolean updateDevice(Device device) {
		boolean retval = false;
		
		for (Device var : devices) {
			if(var.getPk() == device.getPk()) {
				devices.remove(var);
				devices.add(device);
				retval = true;
				break;
			}
		}
		
		return retval;
	}
	
	public boolean processDeviceCommandRefreshState(DeviceCommandRefreshState deviceCommandRefreshState) {
		boolean retval = false;
		
		int deviceID = deviceCommandRefreshState.getDeviceID();
		if(containsDeviceId(deviceID)) {
			Device device = getDevice(deviceID);
			retval = DeviceCommandDispatcher.processCommandRefresh(device, deviceCommandRefreshState);
		}
		
		return retval;
	}

	@JsonGetter("placeName")
	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
}
