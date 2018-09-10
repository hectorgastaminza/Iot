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
 * Servlet implementation class UserConfigServlet
 */
@WebServlet("/userconfig.do")
public class UserConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/userconfig.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loguedUser = (User)request.getSession(false).getAttribute("user");
		String password = request.getParameter("password");
		String confirm = request.getParameter("confirm");
		int pk = loguedUser.getPk();

		if((password.equals(confirm)) && (pk > 0)) {
			String newUsername = request.getParameter("username");
			String newEmail = request.getParameter("email");
			User user = new User(newUsername, password, newEmail);
			user.setPk(pk);
			
			String url = UriComponentsBuilder
					.fromUriString(BackendConfig.USER_UPDATE).toUriString();
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<User> result = null;
			try {
				result = restTemplate.postForEntity(url, BackendConfig.getHttpEntity(user), User.class);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			if((result != null) && (result.getStatusCode() == HttpStatus.OK)) {
				request.setAttribute("successMessage", "User data modified.");
				request.getSession(false).setAttribute("user", user);
			}
			else {
				request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
			}
		}
		else
		{
			request.setAttribute("errorMessage", "Passwords are not equals");
		}

		request.getRequestDispatcher("/WEB-INF/views/userconfig.jsp").forward(request, response);
	}

}
