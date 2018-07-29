package unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import deviceProtocol.ProtocolCommand;
import deviceProtocol.ProtocolSegment;

class TestProtocolCommand {

	@Test
	void test() {
		deviceProtocol.ProtocolCommand command = new ProtocolCommand(new ProtocolSegment[] {
				new ProtocolSegment("I", 2, "Id"),
				new ProtocolSegment("T", 2, "Command"),
				new ProtocolSegment("V", 4, "Value"),
		});
		
		String cmdTest = "I55T04V000A";
		
		assertFalse(command.isContained("I55"));
		assertTrue(command.isContained(cmdTest));
		assertFalse(command.isContained("I5"));
		assertFalse(command.isContained("T04V000A"));
		assertFalse(command.isContained("I555T04V000A"));
		
		assertFalse(command.extractValue("I555T04V000A"));
		assertTrue(command.extractValue(cmdTest));

		assertEquals(command.getValue(0), "55");
		assertEquals(command.getValue(1), "04");
		assertEquals(command.getValue(2), "000A");
		assertEquals(command.getId(0), "I");
		assertEquals(command.getId(1), "T");
		assertEquals(command.getId(2), "V");	
		assertEquals(command.toString(), cmdTest);
	}
}
