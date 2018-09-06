package comiot.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comiot.backend.UserModel;
import comiot.core.device.command.DeviceCommandRequest;

@RestController
public class CommandController {

	@Autowired
	public UserModel userModel;
	
	@RequestMapping("/command/send")
	public boolean getCommandSend(@RequestParam(value="userpk", defaultValue="-1") int userpk, 
			@RequestBody DeviceCommandRequest command) {
		return userModel.commandRequestSend(userpk, command);
	}
	
	boolean commandSend(int userPk, int deviceId, int placeId, int cmd) {
		return false;
	}
}
