package servlet;

import java.io.IOException;
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

import comiot.core.application.server.User;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

/**
 * Servlet implementation class ConnectionServlet
 */
@WebServlet("/connection.do")
public class ConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getConnectionConfig(request);
		request.getRequestDispatcher("/WEB-INF/views/connection.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loguedUser = (User)request.getSession(false).getAttribute("user");
		
		String mqtthost = request.getParameter("mqtthost");
		String mqttport = request.getParameter("mqttport");
		String mqttusername = request.getParameter("mqttusername");
		String mqttpassword = request.getParameter("mqttpassword");
		String mqtttopic = request.getParameter("mqtttopic");
		
		if(mqttport != null)
		{
			int port = Integer.parseInt(mqttport);
			
			MqttConnectionConfiguration mqttConfig = new MqttConnectionConfiguration(mqtthost, 
					port, 
					mqttusername, 
					mqttpassword, 
					mqtttopic);
			
			String url = UriComponentsBuilder
					.fromUriString(BackendConfig.CONNECTION_UPDATE).queryParam("userpk", loguedUser.getPk()).toUriString();
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<MqttConnectionConfiguration> result = null;
			try {
				result = restTemplate.postForEntity(url, BackendConfig.getHttpEntity(mqttConfig), MqttConnectionConfiguration.class);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			if((result != null) && (result.getStatusCode() == HttpStatus.OK)) {
				loguedUser.setMqttConfig(mqttConfig);
				request.getSession(false).removeAttribute("user");
				request.getSession(false).setAttribute("user", loguedUser);
				request.setAttribute("mqttConfig", mqttConfig);
				
				request.setAttribute("successMessage", "Connection data modified.");
			}
			else {
				request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
			}
		}
		else
		{
			request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
		}
		
		request.getRequestDispatcher("/WEB-INF/views/connection.jsp").forward(request, response);
	}
	
	
	private void getConnectionConfig(HttpServletRequest request) {
		User loguedUser = (User)request.getSession(false).getAttribute("user");
		MqttConnectionConfiguration mqttConfig = loguedUser.getMqttConfig();
		
		if(mqttConfig == null) {
			String url = UriComponentsBuilder
					.fromUriString(BackendConfig.CONNECTION_GET).queryParam("userpk", loguedUser.getPk()).toUriString();
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<MqttConnectionConfiguration> result = null;
			try {
				result = restTemplate.exchange(url,
				                    HttpMethod.GET, 
				                    null, 
				                    new ParameterizedTypeReference<MqttConnectionConfiguration>() {}
				);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			if((result != null) && (result.getStatusCode() == HttpStatus.OK)) {
				/* Refresh Places */
				mqttConfig = result.getBody();
			}
		}

		if(mqttConfig != null) {
			request.setAttribute("mqttConfig", mqttConfig);
		}
	}

}
