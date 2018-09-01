package comiot.core.device;

public enum eDeviceStates {
    NONE(0x0),
    OFF(0x1),
    ON(0x2),
    ON_VALUE(0x3);
	private static eDeviceStates[] states = eDeviceStates.values();
	
	private int value;
	
	private eDeviceStates(int value)
	{
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public Boolean isEqual(int value) {
		return (this.value == value);
	}
	
    public static eDeviceStates getFromValue(int _id)
    {
    	eDeviceStates retval = eDeviceStates.NONE;
    	
        for(int i = 0; i < states.length; i++)
        {
            if(states[i].isEqual(_id)) {
            	retval = states[i];
            	break;
            }
        }
        return retval;
    }
}
