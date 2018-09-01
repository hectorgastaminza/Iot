package comiot.core.application.server;

import comiot.core.device.command.DeviceCommandRefreshState;

public interface IDeviceStatusRefreshCallback {
	void deviceRefreshStatusCallback(int userPk, DeviceCommandRefreshState deviceCommandRefreshState);
}
