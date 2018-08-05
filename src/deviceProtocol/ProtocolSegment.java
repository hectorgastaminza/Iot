package deviceProtocol;

public class ProtocolSegment {
	private String description;
	private String localError;	
	private String id;
	private String value;
	private String valueRegex = "^[a-fA-F0-9]+$";
	private int valueLenght;
	private int segmentLenght;
	private int posStart;
	private int posEnd;

	public ProtocolSegment(String id, int valueLenght)
	{
		this.id = id;
		this.valueLenght = valueLenght;
		calculateLenght(id);
	}
	
	public ProtocolSegment(String id, int valueLenght, String description)
	{
		this(id,valueLenght);
		this.description = description;
	}
	
	private void calculateLenght(String id) {
		this.segmentLenght = id.length() + this.valueLenght;
	}	
	
	public String getError() {
		return (description != null) ? description + " : " + localError : localError;
	}

	public String getId() {
		return id;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return id+value;
	}
	
	public int getPosStart() {
		return posStart;
	}
	
	public int getPosEnd() {
		return posEnd;
	}
	
	public int getValueLenght() {
		return valueLenght;
	}
	
	public int getSegmentLenght() {
		return segmentLenght;
	}
	
	public boolean isContained(String param) {
		boolean retval = false;
		localError = "ID not found.";
		int previousIdx = 0;
		
		posStart = param.indexOf(id, previousIdx);
		while(posStart >= 0)
		{
			posEnd = posStart + segmentLenght;
			if(posEnd <= param.length())
			{
				if(valueLenght == 0) {
					retval = true;
					break;
				}
				else {
					String auxValue = param.substring(posStart+id.length(), posEnd);
					if(auxValue.matches(valueRegex))
					{
						retval = true;
						break;
					}
				}
			}
			else
			{
				localError = "Wrong size.";
			}
			previousIdx = posStart + 1;
			posStart = param.indexOf(id, previousIdx);
		}
			
		return retval;
	}
	
	public boolean extractValue(String data) {
		boolean retval = false;		
		localError = null;
		
		if(this.isContained(data))
		{
			value = (valueLenght > 0) ? data.substring(posStart + id.length() , posEnd) : "0";	
			retval = true;	
		}
		
		return retval;
	}
}
