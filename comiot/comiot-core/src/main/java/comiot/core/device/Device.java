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
	
	private int placeID = 0;
	private int id = 0;
	private eDeviceStates state = eDeviceStates.NONE;
	private int value = 0;
	private IDeviceCommandsCallback deviceCommandsCallback = null;
	private String description;
	private String name;
	
	public Device() {
	}
	
	public Device(int place, int id) {
		this.placeID = place;
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public eDeviceStates getState() {
		return state;
	}
	
	public void setState(eDeviceStates state) {
		this.state = state;
	}
	
	public void setState(eDeviceStates state, int value) {
		this.state = state;
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getPlaceID() {
		return placeID;
	}
	
	public void setPlace(int place) {
		this.placeID = place;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setDeviceCommandsCallback(IDeviceCommandsCallback deviceCommandsCallback) {
		this.deviceCommandsCallback = deviceCommandsCallback;
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
	
	
	public boolean equals(Device device) {
		return ((this.id == device.id) && (this.placeID == device.placeID));
	}
	
	public void stateChange(eDeviceStates state) {
		this.state = state;
		
		if(debugMessages) {
			System.out.println("Device: " + id + 
					" | Place: " + placeID +
					" | new state: " + state
					);
		}
		
		refreshState();
	}
	
	public boolean refreshState() {
		return (deviceCommandsCallback != null) ? deviceCommandsCallback.commandRefreshState(placeID, id, state, value) : false;
	}
	
	public boolean valueChange(int value) {
		this.value = value;
		stateChange(eDeviceStates.ON_VALUE);
		return true;
	}
	
	public boolean reset() {
		stateChange(eDeviceStates.NONE);
		return true;
	}
	
	public boolean on() {
		stateChange(eDeviceStates.ON);
		return true;
	}
	
	public boolean off() {
		stateChange(eDeviceStates.OFF);
		return true;
	}
	
	public boolean up() {
		stateChange(eDeviceStates.ON_VALUE);
		return true;
	}
	
	public boolean down() {
		stateChange(eDeviceStates.ON_VALUE);
		return true;
	}
}
