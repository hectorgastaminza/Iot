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
				
				int userPk = (int) request.getSession().getAttribute("userpk");
				
				if(param.getName().equals("on")){
					int deviceId = Integer.parseInt(params.get(0).getValue());
					sendAppCommand(userPk, deviceId, eDeviceCommands.ON);
				}
				else {
					if(param.getName().equals("off")){
						int deviceId = Integer.parseInt(params.get(0).getValue());
						sendAppCommand(userPk, deviceId, eDeviceCommands.OFF);
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
	
	private void sendAppCommand(int userPk, int deviceId, eDeviceCommands command) {

	}
	
}
