package comiot.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comiot.backend.UserModel;
import comiot.core.application.server.User;

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

	@RequestMapping(value = "/user/recovery", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserRecovery(@RequestBody User user)
	{
	    System.out.println(user.getEmail());
	    boolean retval = userModel.userRecovery(user.getEmail());
	    
	    return new ResponseEntity<User>(user, retval? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

}
