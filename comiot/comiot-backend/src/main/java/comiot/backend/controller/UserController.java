package comiot.backend.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.MessagingException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comiot.core.application.server.User;
import comiot.core.database.DBConnector;
import comiot.core.database.mysql.ConnectorMysql;

@RestController
public class UserController {
	
	@RequestMapping("/")
	public String getRoot(@RequestParam(value="name", defaultValue="") String name, @RequestParam(value="password", defaultValue="") String password) {
		return "Hello World!";
	}
	
	@RequestMapping("/user/login")
	public User getUserLogin(@RequestParam(value="name", defaultValue="") String name, @RequestParam(value="password", defaultValue="") String password) {
		return userLogin(name, password);
	}
	
	@RequestMapping("/user/signup")
	public boolean getUserSignup(@RequestParam(value="name", defaultValue="") String name, 
			@RequestParam(value="password", defaultValue="") String password,
			@RequestParam(value="email", defaultValue="") String email) {
		return userSignup(name, password, email);
	}
	
	@RequestMapping("/user/recovery")
	public boolean getUserRecovery(@RequestParam(value="email", defaultValue="") String email) {
		return userRecovery(email);
	}
	
	private User userLogin(String username, String password) {
		User user = new User(username, "", "");
		
		int userPk = 0;
		try {
			Connection conn = ConnectorMysql.getConnection();
			userPk = DBConnector.userGetPk(conn, username, password);
			conn.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (userPk > 0) {
		    user.setPk(userPk);
		}
		
		return user;
	}
	
	private boolean userSignup(String username, String password, String email) {
		int result = -1;
		
		User user = new User(username, password, email);
		
		try {
			Connection conn = ConnectorMysql.getConnection();
			result = DBConnector.userInsert(conn, user);
			conn.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (result > 0);
	}
	
	private boolean userRecovery(String email) {
		boolean retval = false;
		
		User user = null;
		try {
			Connection conn = ConnectorMysql.getConnection();
			user = DBConnector.userGetByEmail(conn, email);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if(user != null) {
			try {
				retval = comiot.core.email.EmailSender.sendRecovery(user);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		
		return retval;
	}

}
