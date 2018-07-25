package Device;
/**
 * Define device available commands
 * 
 * @author USER
 *
 */
public enum eDeviceCommands {
    NONE(0x0), 
    STATUS(0x1),
    SET(0x2),
    OFF(0x3),
    ON(0x4),
	UP(0x5),
	DOWN(0x6);
	
	private int value;
	
	private eDeviceCommands(int value)
	{
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
