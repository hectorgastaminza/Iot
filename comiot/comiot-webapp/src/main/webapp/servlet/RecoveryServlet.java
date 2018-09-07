package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;

import comiot.core.application.server.User;

/**
 * Servlet implementation class RecoveryServlet
 */
@WebServlet("/recovery.do")
public class RecoveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/recovery.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		
		UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(BackendConfig.USER_RECOVERY)
			    // Add query parameter
			    .queryParam("email", email);
		System.out.println(builder.toUriString());
				
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", "application/json");
		
		User user = new User("","",email);
		ObjectMapper objMap = new ObjectMapper();
		String userStr = objMap.writeValueAsString(user);
		
		HttpEntity <String> httpEntity = new HttpEntity <String> (userStr, httpHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(builder.toUriString(), httpEntity, String.class);
		
	    System.out.println(result);
	}

}
