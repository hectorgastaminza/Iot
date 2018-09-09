package servlet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import comiot.core.application.server.User;
import comiot.core.device.command.DeviceCommandRequest;
import comiot.core.device.command.eDeviceCommands;

/**
 * Servlet implementation class DeviceCreateServlet
 */
@WebServlet("/commandsend.do/*")
public class CommandSendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paramsStr = request.getQueryString();
		
		if(paramsStr != null) {
			List<NameValuePair> params = URLEncodedUtils.parse(paramsStr, Charset.forName("UTF-8"));

			if(params != null) {
				NameValuePair param = params.get(0);
				
				User user = (User) request.getSession(false).getAttribute("user");

				if(param.getName().equals("on")){
					int deviceId = Integer.parseInt(params.get(0).getValue());
					sendAppCommand(user.getPk(), deviceId, eDeviceCommands.ON);
				}
				else {
					if(param.getName().equals("off")){
						int deviceId = Integer.parseInt(params.get(0).getValue());
						sendAppCommand(user.getPk(), deviceId, eDeviceCommands.OFF);
					}
				}
			}
		}
		
		request.getRequestDispatcher("/app/home").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	private boolean sendAppCommand(int userPk, int deviceId, eDeviceCommands command) {
		boolean retval = false;
		
		String url = UriComponentsBuilder
				.fromUriString(BackendConfig.COMMAND_SEND)
				.queryParam("userpk", userPk)
				.toUriString();
		
		DeviceCommandRequest cmdRequest = new DeviceCommandRequest(1, deviceId, command, 0);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Boolean> result = null;
		try {
			result = restTemplate.exchange(url,
			                    HttpMethod.POST, 
			                    BackendConfig.getHttpEntity(cmdRequest), 
			                    new ParameterizedTypeReference<Boolean>() {}
			);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if((result != null) && (result.getStatusCode() == HttpStatus.OK)) {
			retval = result.getBody();
		}
		
		return retval;
	}
	
}
