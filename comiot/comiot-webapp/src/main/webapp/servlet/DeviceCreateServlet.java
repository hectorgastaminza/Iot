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

import comiot.core.application.server.Place;
import comiot.core.application.server.User;
import comiot.core.device.Device;

/**
 * Servlet implementation class DeviceCreateServlet
 */
@WebServlet("/devicecreate.do/*")
public class DeviceCreateServlet extends HttpServlet {
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

				if(param.getName().equals("update")){
					deviceUpdate(request, Integer.parseInt(param.getValue()));
				}
				else {
					if(param.getName().equals("delete"))
					{
						deviceDelete(request, Integer.parseInt(param.getValue()));
					}
				}
			}
		}
		
		request.getRequestDispatcher("/WEB-INF/views/devicecreate.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deviceName = request.getParameter("deviceName");
		String deviceDescription = request.getParameter("deviceDescription");
		String deviceIdStr = request.getParameter("deviceId");
		String devicePkStr = request.getParameter("devicePk");
		int deviceId = Integer.parseInt(deviceIdStr);
		int userPk = ((User)request.getSession(false).getAttribute("user")).getPk();
		boolean update = (devicePkStr != null) && (!devicePkStr.isEmpty());
		int devicePk = -1;

		String server = (update) ? BackendConfig.DEVICE_UPDATE : BackendConfig.DEVICE_CREATE;
		String success = (update) ? "Your device's information has been modified!" : "Your new device has been created!";

		Device device = new Device(0, deviceId);
		device.setName(deviceName);
		device.setDescription(deviceDescription);

		if(update) {
			devicePk = Integer.parseInt(devicePkStr);
			device.setPk(devicePk);		
		}

		String url = UriComponentsBuilder
				.fromUriString(server)
				.queryParam("userpk", userPk)
				.toUriString();

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Boolean> result = null;
		try {
			result = restTemplate.postForEntity(url, BackendConfig.getHttpEntity(device), Boolean.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if((result != null) && (result.getStatusCode() == HttpStatus.OK)) {
			request.setAttribute("successMessage", success);
		}
		else {
			request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
			if(update) {
				request.setAttribute("device", device);
			}
		}

		request.getRequestDispatcher("/WEB-INF/views/devicecreate.jsp").forward(request, response);
	}
	
	private void deviceUpdate(HttpServletRequest request, int devicepk) {
		User user = (User) request.getSession(false).getAttribute("user");
		
		String url = UriComponentsBuilder
				.fromUriString(BackendConfig.DEVICE_GET_BY_PK)
				.queryParam("userpk", user.getPk())
				.queryParam("devicepk", devicepk)
				.toUriString();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Device> result = null;
		try {
			result = restTemplate.exchange(url,
			                    HttpMethod.GET, 
			                    null, 
			                    new ParameterizedTypeReference<Device>() {}
			);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if((result != null) && (result.getStatusCode() == HttpStatus.OK)) {
			Device device = result.getBody();
			request.setAttribute("device", device);
		}
	}
	
	
	private void deviceDelete(HttpServletRequest request, int devicepk) {
		User user = (User) request.getSession(false).getAttribute("user");
		
		String url = UriComponentsBuilder
				.fromUriString(BackendConfig.DEVICE_DELETE)
				.queryParam("userpk", user.getPk())
				.queryParam("devicepk", devicepk)
				.toUriString();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Boolean> result = null;
		try {
			result = restTemplate.exchange(url,
			                    HttpMethod.DELETE, 
			                    null, 
			                    new ParameterizedTypeReference<Boolean>() {}
			);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if((result != null) && (result.getStatusCode() == HttpStatus.OK)) {
			request.setAttribute("successMessage", "The device was deleted.");
		}
		else {
			request.setAttribute("errorMessage", "Error. The device could not be deleted.");
		}
	}
}
