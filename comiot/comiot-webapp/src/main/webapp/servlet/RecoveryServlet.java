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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;

import comiot.core.application.server.User;
import comiot.core.database.DBRecord;
import comiot.core.device.Device;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

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
		
		User user = new User("","",email);
		ObjectMapper objMap = new ObjectMapper();
		String userStr = objMap.writeValueAsString(user);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", "application/json");
		HttpEntity <String> httpEntity = new HttpEntity <String> (userStr, httpHeaders);
		
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromUriString(BackendConfig.USER_RECOVERY);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<User> result = null;
		
		try {
			result = restTemplate.postForEntity(builder.toUriString(), httpEntity, User.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if((result != null) && (result.getStatusCode() == HttpStatus.OK)) {
			request.setAttribute("successMessage", "We've sent to you an email with your credentials.");
		}
		else {
			request.setAttribute("errorMessage", "This email has not been registered");
		}
		request.getRequestDispatcher("/WEB-INF/views/recovery.jsp").forward(request, response);
	}
	
	
	/*
	serializerTest(new User(), objMap, User.class);
	serializerTest(new DBRecord(), objMap, DBRecord.class);
	serializerTest(new Device(), objMap, Device.class);
	serializerTest(new MqttConnectionConfiguration(), objMap, MqttConnectionConfiguration.class);
	*/
	static <T> void serializerTest(T var, ObjectMapper objMap, T classType){
		String auxStr = null;
		T aux = null;
		try {
			auxStr = objMap.writeValueAsString(var);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			aux = objMap.readValue(auxStr, (Class<T>) classType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		aux.toString();
	}

}
