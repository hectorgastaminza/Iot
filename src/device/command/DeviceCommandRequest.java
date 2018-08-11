package device;

import java.util.ArrayList;

import deviceProtocol.ProtocolCommand;
import deviceProtocol.ProtocolSegment;

public class DeviceCommandRequest extends DeviceCommand {
	static final private ProtocolSegment COMMAND_HEADER_ID = new ProtocolSegment("RQ", 0, "Request Command");
	static final private ProtocolSegment[] COMMAND_TRAILER = new ProtocolSegment[] {
			new ProtocolSegment("T", 2, "Command ID"),
			new ProtocolSegment("V", 4, "Value"),
		};
	
	private eDeviceCommands cmd = eDeviceCommands.NONE;
	private int value = 0;

	/**
	 * Constructs a device command
	 * @param cmd : eDeviceCommands
	 * @param value : int
	 */
	public DeviceCommandRequest(int place, int device, eDeviceCommands cmd, int value){
		super(place, device);
		this.cmd = cmd;
		this.value = value;
	}

	/**
	 * Constructs a device command from a String
	 * @param command : String
	 */
	public DeviceCommandRequest(String command)
	{
		super(0,0);
		
		int[] values = getValues(command);
		
		/* Assign values */
		this.cmd = eDeviceCommands.getFromValue(values[0]);
		this.value = values[1];
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
	
	@Override
	protected int[] refreshValues() {
		return new int[] {cmd.getValue(), value};
	}
	

	@Override
	protected ProtocolSegment[] getProtocolSegments() {
		InitializeStatics();
		return protocolSegments;
	}
	
	@Override
	protected ProtocolCommand getProtocolCommand() {
		InitializeStatics();
		return protocolCmd;
	}

	static protected ProtocolCommand protocolCmd = null;
	static protected ProtocolSegment[] protocolSegments = null;
	
	static public DeviceCommandRequest[] createDeviceCommandRequest(String data) {
		ArrayList<DeviceCommandRequest> retval = new ArrayList<>();
		String[] commands;
		
		InitializeStatics();
		
		commands = ProtocolCommand.extractCommands(protocolCmd, data);
		
		for (String value : commands) {
			retval.add(new DeviceCommandRequest(value));
		}
		
		return retval.toArray(new DeviceCommandRequest[retval.size()]);
	}
	
	static protected void InitializeStatics() {
		if(protocolSegments == null) {
			protocolSegments = DeviceCommand.createDeviceCommandProtocolSegments(COMMAND_HEADER_ID, COMMAND_TRAILER);
		}	
		if(protocolCmd == null) {
			protocolCmd = new ProtocolCommand(protocolSegments);
		}
	}
}
