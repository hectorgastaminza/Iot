package application.server;

import device.command.DeviceCommandRefreshState;

public interface IDeviceStatusRefreshCallback {
	void deviceRefreshStatusCallback(int userPk, DeviceCommandRefreshState deviceCommandRefreshState);
}
