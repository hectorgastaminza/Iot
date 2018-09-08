package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import comiot.core.application.server.User;

@WebServlet(urlPatterns="/login.do")
public class LoginServlet extends HttpServlet {
	
	//private UserValidationService userValidationService = new UserValidationService();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
	    HttpSession session = request.getSession();
	    session.removeAttribute("user");
		
		User user = new User(username, password, "");
		
		String url = UriComponentsBuilder
				.fromUriString(BackendConfig.USER_LOGIN).toUriString();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<User> result = null;
		try {
			result = restTemplate.postForEntity(url, BackendConfig.getHttpEntity(user), User.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if((result != null) && (result.getStatusCode() == HttpStatus.OK)) {
			User loguedUser = result.getBody();
		    session.setAttribute("user", loguedUser);
			response.sendRedirect("/app/home");
		}
		else {
			request.setAttribute("errorMessage", "Invalid Credentials");
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
	}
}