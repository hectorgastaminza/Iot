package unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.*;
import device.Device;
import device.DeviceCommand;
import device.eDeviceCommands;

class TestDeviceCommand {
	
	@Test
	void test() {
		DeviceCommand cmd1 = new DeviceCommand(0x55, eDeviceCommands.ON, 0x0A);
		String test1 = "I55T04V000A";		
		
		assertEquals(cmd1.toString(), test1);
		
		DeviceCommand cmd2 = new DeviceCommand(test1);
		
		assertTrue(cmd1.equals(cmd2));
	}
	
}
