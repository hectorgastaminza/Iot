package device.command;

import java.util.Arrays;

import device.protocol.ProtocolCommand;
import device.protocol.ProtocolSegment;

/**
 * Define Command structure
 * @author USER
 *
 */
abstract public class DeviceCommand {
	static final private ProtocolSegment SEGMENT_PLACE_ID = new ProtocolSegment("P", 2, "Place ID");
	static final private ProtocolSegment SEGMENT_DEVICE_ID = new ProtocolSegment("I", 2, "Device ID");
	static final private int CMD_OFFSET = 3;
	private int placeID = 0;
	private int deviceID = 0;
	
	abstract protected int[] refreshValues();
	
	abstract protected ProtocolCommand getProtocolCommand();
	
	abstract protected ProtocolSegment[] getProtocolSegments();
	
	public DeviceCommand(int place, int device) {
		this.placeID = place;
		this.deviceID = device;
	}
	
	/**
	 * Gets place ID
	 * @return int
	 */
	public int getPlaceID()
	{
		return placeID;
	}
	
	/**
	 * Gets device ID
	 * @return int
	 */
	public int getDeviceID()
	{
		return deviceID;
	}
	
	/**
	 * Determines if a DeviceCommand is equal to another one
	 * @param deviceCommand
	 * @return
	 */
	public Boolean equals(DeviceCommand deviceCommand) {
		return ((placeID == 0) || (placeID == deviceCommand.placeID)) && ((deviceID == 0) || (deviceID == deviceCommand.deviceID));
	}
	
	/**
	 * Gets a command device in a string format
	 */
	public String toString(){
		String format;
		StringBuilder stringBuilder = new StringBuilder();
		ProtocolSegment[] protocolSegments = getProtocolSegments();
		
		int[] cmds = refreshValues();
		int[] values = new int[CMD_OFFSET + cmds.length];
		values[0] = 0;
		values[1] = placeID;
		values[2] = deviceID;
		for (int i = 0; i < cmds.length; i++) {
			values[i + CMD_OFFSET] = cmds[i];
		}
		
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
	
	protected int[] getValues(String command) {
		int[] retval = null;
		int[] values = null;
		
		ProtocolCommand protocolCmd = getProtocolCommand();
		
		command = command.toUpperCase();
		
		if(protocolCmd.extractValue(command)) {
			/* Generic conversion */
			int segmentsCount = protocolCmd.getSegmentQuantity();
			values = new int[segmentsCount];
			for (int i = 0; i < values.length; i++) {
				values[i] = Integer.parseInt(protocolCmd.getValue(i), 16);
			}
		}
		/* Generic set of values */
		this.placeID = values[1];
		this.deviceID = values[2];
		
		retval = Arrays.copyOfRange(values, 3, values.length);
		
		return retval;
	}
	
	static protected ProtocolSegment[] createDeviceCommandProtocolSegments(ProtocolSegment command, ProtocolSegment[] values) {
		ProtocolSegment[] retval = new ProtocolSegment[CMD_OFFSET + values.length];
		
		retval[0] = command;
		retval[1] = SEGMENT_PLACE_ID;
		retval[2] = SEGMENT_DEVICE_ID;
		for (int i = 0; i < values.length; i++) {
			retval[i + CMD_OFFSET] = values[i];
		}
		
		return retval;
	}
}
