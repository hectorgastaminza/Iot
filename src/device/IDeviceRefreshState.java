package device;

public interface IDeviceRefreshState {
	public boolean refreshState(int placeID, int deviceID, eDeviceStates state, int value);
}
