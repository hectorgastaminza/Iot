package device;

import java.util.ArrayList;

import deviceProtocol.ProtocolCommand;
import deviceProtocol.ProtocolSegment;

/**
 * Define Command structure
 * @author USER
 *
 */
public class DeviceCommand {
	private int device = 0;
	private eDeviceCommands cmd = eDeviceCommands.NONE;
	private int value = 0;	
	
	/**
	 * Constructs a device command
	 * @param cmd : eDeviceCommands
	 * @param value : int
	 */
	public DeviceCommand(int device, eDeviceCommands cmd, int value){
		this.device = device;
		this.cmd = cmd;
		this.value = value;
	}
		
	/**
	 * Constructs a device command from a String
	 * @param command : String
	 */
	public DeviceCommand(String command)
	{
		ProtocolCommand protocolCmd = getProtocolCommand();
				
		command = command.toUpperCase();
		
		if(protocolCmd.extractValue(command)) {
			try {
				this.device = Integer.parseInt(protocolCmd.getValue(0), 16);

				int id = Integer.parseInt(protocolCmd.getValue(1), 16);				
				for (eDeviceCommands var : eDeviceCommands.values()) {
					if(var.getValue() == id)
					{
						this.cmd = var;
						break;
					}
				}
				
				this.value = Integer.parseInt(protocolCmd.getValue(2), 16);				
			} catch (Exception e) {
			}		
		}
	}

	/**
	 * Gets device command ID
	 * @return eDeviceCommands
	 */
	public eDeviceCommands getId()
	{
		return cmd;
	}
	
	/**
	 * Gets device command value
	 * @return int
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * Gets a command device in a string format
	 */
	public String toString(){
		String deviceFormat = "%0" + String.valueOf(deviceLength) + "X";
		String cmdFormat = "%0" + String.valueOf(cmdLength) + "X";
		String valueFormat = "%0" + String.valueOf(valueLenght) + "X";
		String retval = deviceID + String.format(deviceFormat, device) + cmdID + String.format(cmdFormat, cmd.getValue()) + valueID + String.format(valueFormat, value);
		return retval;
	}
	
	/**
	 * Determines if a DeviceCommand is equal to another one
	 * @param deviceCommand
	 * @return
	 */
	public Boolean equals(DeviceCommand deviceCommand) {
		return ((device == 0) || (device == deviceCommand.device)) && (cmd == deviceCommand.cmd) && (value == deviceCommand.value);
	}
	
	static private final String deviceID = "I";
	static private final int deviceLength = 2;
	static private final String cmdID = "T";
	static private final int cmdLength = 2;
	static private final String valueID = "V";
	static private final int valueLenght = 4;
	static private ProtocolCommand protocolCmd;
		
	static private ProtocolCommand getProtocolCommand() {
		if(protocolCmd == null) {
			protocolCmd = new ProtocolCommand(new ProtocolSegment[] {
						new ProtocolSegment(deviceID, deviceLength, "Device ID"),
						new ProtocolSegment(cmdID, cmdLength, "Command ID"),
						new ProtocolSegment(valueID, valueLenght, "Value"),
				});
		}
		
		return protocolCmd;
	}
	
	public static DeviceCommand[] createDeviceCommand(String data) {
		ArrayList<DeviceCommand> retval = new ArrayList<>();
		String[] commands = ProtocolCommand.extractCommands(getProtocolCommand(), data);	
		
		for (String value : commands) {
			retval.add(new DeviceCommand(value));
		}
		
		return retval.toArray(new DeviceCommand[retval.size()]);
	}
}
