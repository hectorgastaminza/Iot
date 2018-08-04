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
	private int place = 0;
	private int device = 0;
	private eDeviceCommands cmd = eDeviceCommands.NONE;
	private int value = 0;	
	
	/**
	 * Constructs a device command
	 * @param cmd : eDeviceCommands
	 * @param value : int
	 */
	public DeviceCommand(int place, int device, eDeviceCommands cmd, int value){
		this.place = place;
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
				/* Generic conversion */
				int segmentsCount = protocolCmd.getSegmentQuantity();
				int[] values = new int[segmentsCount];
				for (int i = 0; i < values.length; i++) {
					values[i] = Integer.parseInt(protocolCmd.getValue(i), 16);
				}
				/* Assign values */
				this.place = values[0];
				this.device = values[1];
				this.cmd = eDeviceCommands.NONE;
				this.value = values[3];
				for (eDeviceCommands var : eDeviceCommands.values()) {
					if(var.getValue() == values[2])
					{
						this.cmd = var;
						break;
					}
				}			
			} catch (Exception e) {
			}		
		}
	}

	/**
	 * Gets place command ID
	 * @return eDeviceCommands
	 */
	public int getPlace()
	{
		return place;
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
	
	protected int[] getValuesToSend( ) {
		return new int[] {place, device, cmd.getValue(), value};
	}
	
	/**
	 * Gets a command device in a string format
	 */
	public String toString(){
		int[] values = getValuesToSend();
		String format;
		StringBuilder stringBuilder = new StringBuilder();
		/* Generic conversion */
		for (int idx = 0; idx < protocolSegments.length; idx++) {
			stringBuilder.append(protocolSegments[idx].getId());
			if(protocolSegments[idx].getValueLenght() > 0) {
				format = "%0" + String.valueOf(protocolSegments[idx].getValueLenght()) + "X";
				stringBuilder.append(String.format(format, values[idx])); 
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * Determines if a DeviceCommand is equal to another one
	 * @param deviceCommand
	 * @return
	 */
	public Boolean equals(DeviceCommand deviceCommand) {
		return ((place == 0) || (device == deviceCommand.device)) && ((device == 0) || (device == deviceCommand.device)) && (cmd == deviceCommand.cmd) && (value == deviceCommand.value);
	}
	
	static private final String placeID = "P";
	static private final int placeLength = 2;
	static private final String deviceID = "I";
	static private final int deviceLength = 2;
	static private final String cmdID = "T";
	static private final int cmdLength = 2;
	static private final String valueID = "V";
	static private final int valueLenght = 4;
	static private ProtocolCommand protocolCmd;
	static private ProtocolSegment[] protocolSegments = new ProtocolSegment[] {
			new ProtocolSegment(placeID, placeLength, "Place ID"),
			new ProtocolSegment(deviceID, deviceLength, "Device ID"),
			new ProtocolSegment(cmdID, cmdLength, "Command ID"),
			new ProtocolSegment(valueID, valueLenght, "Value"),
	};
		
	static private ProtocolCommand getProtocolCommand() {
		if(protocolCmd == null) {
			protocolCmd = new ProtocolCommand(protocolSegments);
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
