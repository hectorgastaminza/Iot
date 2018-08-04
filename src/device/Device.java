package device;

public class Device {
	private int place = 0;
	private int id = 0;
	private eDeviceStates state = eDeviceStates.NONE;
	private int value = 0;
	private IDeviceRefreshState callbackRefreshState = null;
	private String description;
	
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
	
	public void setCallbackRefreshState(IDeviceRefreshState callbackRefreshState) {
		this.callbackRefreshState = callbackRefreshState;
	}
	
	public void setState(eDeviceStates state) {
		this.state = state;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean setValue(int value) {
		this.value = value;
		return true;
	}
	
	public int getPlace() {
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
		return true;
	}
	
	public boolean on() {
		return true;
	}
	
	public boolean off() {
		return true;
	}
	
	public boolean up() {
		return true;
	}
	
	public boolean down() {
		return true;
	}
	
	public boolean refreshState() {
		return (callbackRefreshState != null) ? callbackRefreshState.refreshState(place, id, state, value) : false;
	}
}
