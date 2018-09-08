package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import comiot.core.application.server.User;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet("/signup.do")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String password = request.getParameter("password");
		String confirm = request.getParameter("confirm");

		if(password.equals(confirm)) {
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			User user = new User(username, password, email);
			
			String url = UriComponentsBuilder
					.fromUriString(BackendConfig.USER_SIGNUP).toUriString();
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<User> result = null;
			try {
				result = restTemplate.postForEntity(url, BackendConfig.getHttpEntity(user), User.class);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			if((result != null) && (result.getStatusCode() == HttpStatus.OK)) {
				request.setAttribute("successMessage", "User created. Please login");
			}
			else {
				request.setAttribute("errorMessage", "This user is already been registered");
			}
		}
		else {
			request.setAttribute("errorMessage", "Passwords are not equals");
		}
		
		request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
	}
}
