package comiot.core.device;

import java.io.Serializable;

import comiot.core.database.DBRecord;
import comiot.core.device.command.IDeviceCommandsCallback;

public class Device extends DBRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int DEVICE_ID_INVALID = 0x00;
	public static final int DEVICE_ID_BROADCAST = 0xFF;
	public static final int DEVICE_ID_MIN = DEVICE_ID_INVALID + 1;
	public static final int DEVICE_ID_MAX = DEVICE_ID_BROADCAST - 1;
	
	private int place = 0;
	private int id = 0;
	private eDeviceStates state = eDeviceStates.NONE;
	private int value = 0;
	private IDeviceCommandsCallback deviceCommandsCallback = null;
	private String description;
	private String name;
	
	public Device(int place, int id) {
		this.place = place;
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean equals(Device device) {
		return ((this.id == device.id) && (this.id == device.id));
	}
	
	public eDeviceStates getState() {
		return state;
	}
	
	public void setDeviceCommandsCallback(IDeviceCommandsCallback deviceCommandsCallback) {
		this.deviceCommandsCallback = deviceCommandsCallback;
	}
	
	public void setState(eDeviceStates state, int value) {
		this.state = state;
		this.value = value;
	}
	
	public void setState(eDeviceStates state) {
		this.state = state;
		
		if(debugMessages) {
			System.out.println("Device: " + id + 
					" | Place: " + place +
					" | new state: " + state
					);
		}
		
		refreshState();
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean setValue(int value) {
		this.value = value;
		setState(eDeviceStates.ON_VALUE);
		return true;
	}
	
	public int getPlaceID() {
		return place;
	}
	
	public void setPlace(int place) {
		this.place = place;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean reset() {
		setState(eDeviceStates.NONE);
		return true;
	}
	
	public boolean on() {
		setState(eDeviceStates.ON);
		return true;
	}
	
	public boolean off() {
		setState(eDeviceStates.OFF);
		return true;
	}
	
	public boolean up() {
		setState(eDeviceStates.ON_VALUE);
		return true;
	}
	
	public boolean down() {
		setState(eDeviceStates.ON_VALUE);
		return true;
	}
	
	public boolean refreshState() {
		return (deviceCommandsCallback != null) ? deviceCommandsCallback.commandRefreshState(place, id, state, value) : false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private boolean debugMessages = false;
	public void setDebugMessages(boolean debug) {
		debugMessages = debug;
	}
}
