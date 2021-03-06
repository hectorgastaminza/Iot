package comiot.core.device.command;

import comiot.core.device.eDeviceStates;

public interface IDeviceCommandsCallback {
	public boolean commandRefreshState(int placeID, int deviceID, eDeviceStates state, int value);
	
	public boolean commandRequest(int placeID, int deviceID, eDeviceCommands commandID, int value);
}
