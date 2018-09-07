package comiot.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comiot.backend.UserModel;
import comiot.core.application.server.User;
import comiot.core.device.Device;

@RestController
public class UserController {
	
	@Autowired
	public UserModel userModel;
	
	@RequestMapping("/")
	public String getRoot(@RequestParam(value="name", defaultValue="") String name, 
			@RequestParam(value="password", defaultValue="") String password) {
		return "Hello World!";
	}
	
	@RequestMapping("/user/login")
	public User getUserLogin(@RequestParam(value="name", defaultValue="") String name, 
			@RequestParam(value="password", defaultValue="") String password) {
		return userModel.userLogin(name, password);
	}
	
	@RequestMapping("/user/signup")
	public boolean getUserSignup(@RequestParam(value="name", defaultValue="") String name, 
			@RequestParam(value="password", defaultValue="") String password,
			@RequestParam(value="email", defaultValue="") String email) {
		return userModel.userSignup(name, password, email);
	}

	@RequestMapping(value = "/user/recovery", method = RequestMethod.POST)
	public ResponseEntity<String> getUserRecovery(@RequestParam(value="email", defaultValue="") String email,
			@RequestBody User user)
	{
	    System.out.println(user.getEmail());
	    return new ResponseEntity<String>(user.getEmail(), userModel.userRecovery(user.getEmail()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

}
