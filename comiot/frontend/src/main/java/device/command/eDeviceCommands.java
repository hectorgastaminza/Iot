package device.command;
/**
 * Define device available commands
 * 
 * @author USER
 *
 */
public enum eDeviceCommands {
    NONE(0x0, "None"),
    GET_STATUS(0x1, "Get status"),
    SET_VALUE(0x2, "Set value"),
    OFF(0x3, "Off"),
    ON(0x4, "On"),
	UP(0x5, "Up"),
	DOWN(0x6, "Down"),
	RESET(0xFF, "Reset");
	
	private int value;
	private String name;
	private static eDeviceCommands[] commands = eDeviceCommands.values();
	
	private eDeviceCommands(int value, String name)
	{
		this.value = value;
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public Boolean isEqual(int value) {
		return (this.value == value);
	}
	
    public static eDeviceCommands getFromValue(int _id)
    {
    	eDeviceCommands retval = eDeviceCommands.NONE;
    	
        for(int i = 0; i < commands.length; i++)
        {
            if(commands[i].isEqual(_id)) {
            	retval = commands[i];
            	break;
            }
        }
        return retval;
    }
}
