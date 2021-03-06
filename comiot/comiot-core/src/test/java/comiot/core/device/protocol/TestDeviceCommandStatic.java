package comiot.core.device.protocol;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import comiot.core.device.command.DeviceCommandRequest;

class TestDeviceCommandStatic {

	@Test
	void test() {
		String cmdTest = "RQP01I55T04V000A";

		assertTrue(DeviceCommandRequest.createDeviceCommandRequest(cmdTest + "A" + cmdTest + "A" + cmdTest).length == 3);
		assertTrue(DeviceCommandRequest.createDeviceCommandRequest(cmdTest + "I" + cmdTest + "T" + cmdTest).length == 3);
	}
}
