package comiot.core.device.protocol;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import comiot.core.device.protocol.ProtocolCommand;
import comiot.core.device.protocol.ProtocolSegment;

class TestProtocolCommand {

	@Test
	void test() {
		comiot.core.device.protocol.ProtocolCommand sut = new ProtocolCommand(new ProtocolSegment[] {
				new ProtocolSegment("I", 2, "Id"),
				new ProtocolSegment("T", 2, "Command"),
				new ProtocolSegment("V", 4, "Value"),
		});
		
		String cmdTest = "I55T04V000A";
		
		assertFalse(sut.isContained("I55"));
		assertTrue(sut.isContained(cmdTest));
		assertFalse(sut.isContained("I5"));
		assertFalse(sut.isContained("T04V000A"));
		assertFalse(sut.isContained("I555T04V000A"));
		
		assertFalse(sut.extractValue("I555T04V000A"));
		assertTrue(sut.extractValue(cmdTest));

		assertEquals(sut.getValue(0), "55");
		assertEquals(sut.getValue(1), "04");
		assertEquals(sut.getValue(2), "000A");
		assertEquals(sut.getId(0), "I");
		assertEquals(sut.getId(1), "T");
		assertEquals(sut.getId(2), "V");	
		assertEquals(sut.toString(), cmdTest);
	}
}
