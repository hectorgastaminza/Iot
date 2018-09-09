package comiot.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Boolean> getCommandSend(@RequestParam(value="userpk", defaultValue="-1") int userpk, 
			@RequestBody DeviceCommandRequest command) {
		boolean retval =  userModel.commandRequestSend(userpk, command);
		return new ResponseEntity<Boolean>(retval, retval? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
