package unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import device.DeviceCommandRequest;
import device.eDeviceCommands;

class TestDeviceCommand {
	
	@Test
	void test() {
		DeviceCommandRequest cmd1 = new DeviceCommandRequest(0x22, 0x55, eDeviceCommands.ON, 0x0A);
		String expected = "RQP22I55T04V000A";	
		String actual = cmd1.toString();
		
		assertEquals(actual, expected);
		
		DeviceCommandRequest cmd2 = new DeviceCommandRequest(expected);
		
		assertTrue(cmd1.equals(cmd2));
	}
	
}
