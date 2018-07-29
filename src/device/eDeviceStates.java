package device;

public enum eDeviceStates {
    NONE(0x0),
    OFF(0x1),
    ON(0x2),
    ON_VALUE(0x3);
	
	private int value;
	
	private eDeviceStates(int value)
	{
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
