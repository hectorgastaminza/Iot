package comiot.client.virtual;

public enum eDemoValues {
	PLACE_ID_GENERIC(1),
	DEVICE_ID_GENERIC(5),
	PLACE_ID_RASPBERRY(1),
	DEVICE_ID_RASPBERRY(9);

	private int value;

	private eDemoValues(int value)
	{
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
