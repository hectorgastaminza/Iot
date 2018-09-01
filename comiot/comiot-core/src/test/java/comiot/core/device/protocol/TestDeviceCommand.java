package comiot.core.device.protocol;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import comiot.core.device.command.DeviceCommandRequest;
import comiot.core.device.command.eDeviceCommands;

class TestDeviceCommand {
	
	@Test
	void test() {
		DeviceCommandRequest sut = new DeviceCommandRequest(0x22, 0x55, eDeviceCommands.ON, 0x0A);
		String expected = "RQP22I55T04V000A";	
		String actual = sut.toString();
		
		assertEquals(actual, expected);
		
		/* use the name: sut (system under test) to the class object which I am going to test */
		
		DeviceCommandRequest cmd2 = new DeviceCommandRequest(expected);
		
		assertTrue(sut.equals(cmd2));
	}
	
}
