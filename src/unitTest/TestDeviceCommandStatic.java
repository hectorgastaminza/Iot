package unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import device.DeviceCommand;
import deviceProtocol.ProtocolCommand;
import deviceProtocol.ProtocolSegment;

class TestDeviceCommandStatic {

	@Test
	void test() {
		String cmdTest = "P01I55T04V000A";

		assertTrue(DeviceCommand.createDeviceCommand(cmdTest + "A" + cmdTest + "A" + cmdTest).length == 3);
		assertTrue(DeviceCommand.createDeviceCommand(cmdTest + "I" + cmdTest + "T" + cmdTest).length == 3);
	}
}
