package comiot.core.device.protocol;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import comiot.core.device.protocol.ProtocolCommand;
import comiot.core.device.protocol.ProtocolSegment;

class TestProtocolCommandStatic {

	@Test
	void test() {

		comiot.core.device.protocol.ProtocolCommand command = new ProtocolCommand(new ProtocolSegment[] {
				new ProtocolSegment("I", 2, "Id"),
				new ProtocolSegment("T", 2, "Command"),
				new ProtocolSegment("V", 4, "Value"),
		});

		String cmdTest = "I55T04V000A";

		assertTrue(ProtocolCommand.extractCommands(command, cmdTest + "A" + cmdTest + "A" + cmdTest).length == 3);
		assertTrue(ProtocolCommand.extractCommands(command, cmdTest + "I" + cmdTest + "T" + cmdTest).length == 3);
	}
}
