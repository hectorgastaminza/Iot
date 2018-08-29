import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import device.protocol.ProtocolSegment;

class TestProtocolSegment {

	@Test
	void test() {
		device.protocol.ProtocolSegment segment = new ProtocolSegment("I", 2, "Id");
		String cmdTest = "I55T04V000A";
		
		assertTrue(segment.isContained("I55"));
		assertFalse(segment.isContained("IGG"));
		assertTrue(segment.isContained("II55"));
		assertFalse(segment.isContained("55I5"));
		assertTrue(segment.isContained(cmdTest));
		assertFalse(segment.isContained("I5"));
		assertFalse(segment.isContained("T04V000A"));
		
		assertTrue(segment.extractValue(cmdTest));

		assertEquals(segment.getId(), "I");
		assertEquals(segment.getValue(), "55");		
		assertEquals(segment.toString(), "I55");
		
		assertEquals(cmdTest.substring(segment.getPosEnd(), cmdTest.length()), "T04V000A");
	}
}
