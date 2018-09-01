package comiot.core.device.protocol;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import comiot.core.device.protocol.ProtocolSegment;

class TestProtocolSegment {

	@Test
	void test() {
		comiot.core.device.protocol.ProtocolSegment sut = new ProtocolSegment("I", 2, "Id");
		String cmdTest = "I55T04V000A";
		
		assertTrue(sut.isContained("I55"));
		assertFalse(sut.isContained("IGG"));
		assertTrue(sut.isContained("II55"));
		assertFalse(sut.isContained("55I5"));
		assertTrue(sut.isContained(cmdTest));
		assertFalse(sut.isContained("I5"));
		assertFalse(sut.isContained("T04V000A"));
		
		assertTrue(sut.extractValue(cmdTest));

		assertEquals(sut.getId(), "I");
		assertEquals(sut.getValue(), "55");		
		assertEquals(sut.toString(), "I55");
		
		assertEquals(cmdTest.substring(sut.getPosEnd(), cmdTest.length()), "T04V000A");
	}
}
