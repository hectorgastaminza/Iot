package comiot.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController {

	@RequestMapping("/command/send")
	public boolean getCommandSend(@RequestParam(value="userpk", defaultValue="-1") int userpk, 
			@RequestParam(value="device", defaultValue="-1") int device,
			@RequestParam(value="place", defaultValue="-1") int place,
			@RequestParam(value="cmd", defaultValue="-1") int cmd) {
		return commandSend(userpk, device, place, cmd);
	}
	
	boolean commandSend(int userPk, int deviceId, int placeId, int cmd) {
		return false;
	}
}
