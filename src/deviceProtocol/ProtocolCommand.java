package deviceProtocol;

import java.util.ArrayList;

public class ProtocolCommand {
	private String description;
	private String localError;
	private ProtocolSegment[] segments;
	private int commandLenght;
	private int posStart;
	private int posEnd;	
	
	public ProtocolCommand(ProtocolSegment[] segments) {
		this.segments = segments;
		calculateLenght(segments);
	}

	private void calculateLenght(ProtocolSegment[] segments) {
		for (ProtocolSegment protocolSegment : segments) {
			commandLenght+=protocolSegment.getSegmentLenght();
		}
	}

	public ProtocolCommand(ProtocolSegment[] segments, String description) {
		this(segments);
		this.description = description;
	}
	
	public String getError() {
		return (description != null) ? description + " : " + localError : localError;
	}
	
	public String getValue(int posSegment) {
		ProtocolSegment segment = getSegment(posSegment);
		return (segment != null) ? segment.getValue() : null;
	}
	
	public String getId(int posSegment) {
		ProtocolSegment segment = getSegment(posSegment);
		return (segment != null) ? segment.getId() : null;
	}

	private ProtocolSegment getSegment(int pos) {
		ProtocolSegment segment = null;
		
		if((segments != null) && (segments.length > pos)) {
			segment = segments[pos];
		}
		
		return segment;
	}
	
	public String toString() {
		String retval = "";
		
		for (ProtocolSegment protocolSegment : segments) {
			retval += protocolSegment.toString();
		}
		
		return retval;
	}
	
	public boolean isContained(String data) {
		boolean retval = false;
		localError = null;
		
		if((segments != null) && (segments.length > 0) && (data.length() >= commandLenght)) {
			retval = true;			
			for (ProtocolSegment protocolSegment : segments) {
				if(!protocolSegment.isContained(data)) {
					retval = false;
					localError = protocolSegment.getError();
					break;
				}
			}
			
			if(retval) {
				posStart = segments[0].getPosStart();
				posEnd = segments[segments.length-1].getPosEnd();
				if(posStart + commandLenght != posEnd) {
					localError = "Wrong data between segments.";
					retval = false;
				}
			}
		}
		
		return retval;
	}
	
	public boolean extractValue(String data) {
		boolean retval = false;
		localError = null;
		
		if(isContained(data)) {
			retval = true;
			for (ProtocolSegment protocolSegment : segments) {
				if(!protocolSegment.extractValue(data)) {
					retval = false;
					localError = protocolSegment.getError();
					break;
				}
			}
		}
	
		return retval;
	}
	
	
	public static String[] extractCommands(ProtocolCommand command, String data) {
		ArrayList<String> retval = new ArrayList<>();
		
		while(command.isContained(data))
		{
			retval.add(data.substring(command.posStart, command.posEnd));
			data = data.substring(command.posEnd, data.length());
		}
		
		return retval.toArray(new String[retval.size()]);
	}
	
}
