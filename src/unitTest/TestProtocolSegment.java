package unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import deviceProtocol.ProtocolSegment;

class TestProtocolSegment {

	@Test
	void test() {
		deviceProtocol.ProtocolSegment segment = new ProtocolSegment("I", 2, "Id");
		String cmdTest = "I55T04V000A";
		
		assertTrue(segment.isContained("I55"));
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
