package deviceProtocol;

public class ProtocolSegment {
	private String description;
	private String localError;	
	private String id;
	private String value;
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

	private void calculateLenght(String id) {
		this.segmentLenght = id.length() + this.valueLenght;
	}
	
	public ProtocolSegment(String id, int valueLenght, String description)
	{
		this(id,valueLenght);
		this.description = description;
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
	
	public int getSegmentLenght() {
		return segmentLenght;
	}
	
	public boolean isContained(String param) {
		boolean retval = false;
		localError = null;
		
		if((param != null) && (param.length() >= segmentLenght)) {
			posStart = param.indexOf(id);
			if(posStart > -1) {
				posEnd = posStart + segmentLenght;
				retval = true;
			}
			else
			{
				localError = "ID not found.";
			}
		}
		else
		{
			localError = "Wrong size.";
		}
			
		return retval;
	}
	
	public boolean extractValue(String data) {
		boolean retval = false;		
		localError = null;
		
		if(this.isContained(data))
		{
			value = data.substring(posStart + id.length() , posEnd);	
			retval = true;	
		}
		
		return retval;
	}
}
