package unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import device.command.DeviceCommandRefreshState;

class TestDeviceCommandRefreshStateStatic {

	@Test
	void test() {
		String cmdTest = "RSP01I55S04V000A";

		assertTrue(DeviceCommandRefreshState.createDeviceCommandRefreshState(cmdTest + "A" + cmdTest + "A" + cmdTest).length == 3);
		assertTrue(DeviceCommandRefreshState.createDeviceCommandRefreshState(cmdTest + "I" + cmdTest + "T" + cmdTest).length == 3);
	}

}
