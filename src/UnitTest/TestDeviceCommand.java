package UnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Application.*;
import Device.Device;
import Device.DeviceCommand;
import Device.eDeviceCommands;

class TestDeviceCommand {
	
	@Test
	void test() {
		DeviceCommand cmd1 = new DeviceCommand(eDeviceCommands.ON, 10);
		String test1 = "C0004X000A";		
		
		assertEquals(cmd1.toString(), test1);
		
		DeviceCommand cmd2 = new DeviceCommand(test1);
		
		assertTrue(cmd1.equals(cmd2));
	}
	
}
