package servlet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import comiot.core.database.DBConnector;
import comiot.core.database.mysql.ConnectorMysql;
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
					try {
						int devicePk = Integer.parseInt(params.get(0).getValue());
						Connection conn = ConnectorMysql.getConnection();
						Device device = DBConnector.deviceGetByPk(conn, devicePk);
						conn.close();
						request.setAttribute("device", device);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else {
					if(param.getName().equals("delete"))
					{
						try {
							int devicePk = Integer.parseInt(params.get(0).getValue());
							Connection conn = ConnectorMysql.getConnection();
							DBConnector.deviceDelete(conn, devicePk);
							conn.close();
							// request.getRequestDispatcher("/login").forward(request, response);
						} catch (SQLException e) {
							e.printStackTrace();
						}
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
		
		try {
			Connection conn = ConnectorMysql.getConnection();
			if((devicePkStr != null) && (!devicePkStr.isEmpty())) {
				devicePk = Integer.parseInt(devicePkStr);
				device.setPk(devicePk);
				result = DBConnector.deviceUpdate(conn, device);
				
				if(result > 0) {
					request.setAttribute("successMessage", "Your new device has been created!");
				}
				else {
					request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
				}
			}
			else
			{
				result = DBConnector.deviceInsert(conn, userPk, device);
				
				if(result > 0) {
					request.setAttribute("successMessage", "Your new device has been created!");
				}
				else {
					request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
				}
			}			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/WEB-INF/views/devicecreate.jsp").forward(request, response);
	}
}
