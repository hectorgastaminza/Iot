package device;

public class DeviceCommandDispatcher {
	
	public static boolean processCommand(Device device, DeviceCommand command)
	{
		boolean retval = false;
		
		switch (command.getId()) {
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
			retval = device.setValue(command.getValue());;
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
