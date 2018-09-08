package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import comiot.core.application.server.DeviceServer;
import comiot.core.application.server.Place;
import comiot.core.application.server.User;
import comiot.core.database.mysql.ConnectorMysql;
import comiot.core.device.Device;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home.do")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		refreshData(request, response);
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	private void refreshData(HttpServletRequest request, HttpServletResponse response) {
		/* Checks if the user was not created */
		User user = (User) request.getSession(false).getAttribute("user");
		
		String url = UriComponentsBuilder
				.fromUriString(BackendConfig.DEVICE_GET).queryParam("userpk", user.getPk()).toUriString();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Place>> result = null;
		try {
			result = restTemplate.exchange(url,
			                    HttpMethod.GET, 
			                    null, 
			                    new ParameterizedTypeReference<List<Place>>() {}
			);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if((result != null) && (result.getStatusCode() == HttpStatus.OK)) {
			/* Refresh Places */
			List<Place> places = result.getBody();
			request.setAttribute("places", places);
		}
	}
}
