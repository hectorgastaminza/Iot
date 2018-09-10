package comiot.core.device.command;

import java.util.ArrayList;

import comiot.core.device.eDeviceStates;
import comiot.core.device.protocol.ProtocolCommand;
import comiot.core.device.protocol.ProtocolSegment;

public class DeviceCommandRefreshState extends DeviceCommand {
	
	static final private ProtocolSegment COMMAND_HEADER_ID = new ProtocolSegment("RS", 0, "Refresh State Command");
	static final private ProtocolSegment[] COMMAND_TRAILER = new ProtocolSegment[] {
			new ProtocolSegment("S", 2, "State ID"),
			new ProtocolSegment("V", 4, "Value"),
		};
	
	private eDeviceStates state = eDeviceStates.NONE;
	private int value = 0;
	
	
	public DeviceCommandRefreshState(int place, int device, eDeviceStates state, int value) {
		super(place, device);
		this.state = state;
		this.value = value;
	}
	
	/**
	 * Constructs a device command from a String
	 * @param command : String
	 */
	public DeviceCommandRefreshState(String command)
	{
		super(0,0);
		
		int[] values = getValues(command);
		
		/* Assign values */
		value = values[1];
		state = eDeviceStates.NONE;
		for (eDeviceStates var : eDeviceStates.values()) {
			if(var.getValue() == values[0])
			{
				state = var;
				break;
			}
		}
	}

	/**
	 * Gets device state ID
	 * @return eDeviceStates
	 */
	public eDeviceStates getState()
	{
		return state;
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
		return new int[] {this.state.getValue(), this.value};
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
	
	static public DeviceCommandRefreshState[] createDeviceCommandRefreshState(String data) {
		ArrayList<DeviceCommandRefreshState> retval = new ArrayList<>();
		String[] commands;
		
		InitializeStatics();
		
		commands = ProtocolCommand.extractCommands(protocolCmd, data);
		
		for (String value : commands) {
			retval.add(new DeviceCommandRefreshState(value));
		}
		
		return retval.toArray(new DeviceCommandRefreshState[retval.size()]);
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
