package comiot.core.device.command;

import comiot.core.device.Device;

public class DeviceCommandDispatcher {
	
	public static boolean processCommandRequest(Device device, DeviceCommandRequest command){
		return processCommand(device, command.getId(), command.getValue());
	}
	
	public static boolean processCommandRefresh(Device device, DeviceCommandRefreshState deviceCommandRefreshState) {
		device.setState(deviceCommandRefreshState.getState(), deviceCommandRefreshState.getValue());
		return true;
	}
	
	public static boolean processCommand(Device device, eDeviceCommands command, int value) {
		boolean retval = false;
		
		switch (command) {
		case RESET:
			retval = device.reset();
			break;
		case OFF:
			retval = device.off();
			break;
		case ON:
			retval = device.on();
			break;
		case SET_VALUE:
			retval = device.setValue(value);
			break;
		case UP:
			retval = device.up();
			break;
		case DOWN:
			retval = device.down();
			break;
		case GET_STATUS:
			retval = device.refreshState();
			break;
		default:
			// error
			break;
		}
		
		return retval;
	}
}
