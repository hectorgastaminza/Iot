package comiot.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping("/user/recovery")
	public boolean getUserRecovery(@RequestParam(value="email", defaultValue="") String email) {
		return userModel.userRecovery(email);
	}

}
