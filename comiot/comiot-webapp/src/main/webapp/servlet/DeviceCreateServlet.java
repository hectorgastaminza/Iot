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

				}
				else {
					if(param.getName().equals("delete"))
					{

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
		int userPk = (int)request.getSession().getAttribute("userpk");
		int devicePk = -1;

		Device device = new Device(0, deviceId);
		device.setName(deviceName);
		device.setDescription(deviceDescription);
		int result = -1;
		
			if((devicePkStr != null) && (!devicePkStr.isEmpty())) {
				devicePk = Integer.parseInt(devicePkStr);
				device.setPk(devicePk);
				result = 1;
				
				if(result > 0) {
					request.setAttribute("successMessage", "Your new device has been created!");
				}
				else {
					request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
				}
			}
			else
			{
				
				if(result > 0) {
					request.setAttribute("successMessage", "Your new device has been created!");
				}
				else {
					request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
				}
			}
		
		request.getRequestDispatcher("/WEB-INF/views/devicecreate.jsp").forward(request, response);
	}
}
