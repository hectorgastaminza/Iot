package application;

import java.util.ArrayList;

import device.Device;

public class Place {
	private String mqttTopic;
	private String description;
	private ArrayList<Device> devices;
	
	public Place(String mqttTopic) {
		this.mqttTopic = mqttTopic;
		devices = new ArrayList<>();
	}
	
	public String getTopic() {
		return mqttTopic;
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
	
	public boolean addDevice(Device device) {
		boolean retval = false;

		if(containsDeviceId(device.getId()) >= 0) {
			devices.add(device);
			retval = true;
		}
		
		return retval;
	}
}
