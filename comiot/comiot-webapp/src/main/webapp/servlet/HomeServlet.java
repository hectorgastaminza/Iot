package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comiot.core.application.common.AppConnection;
import comiot.core.application.server.DeviceServer;
import comiot.core.application.server.Place;
import comiot.core.application.server.User;
import comiot.core.database.DBConnector;
import comiot.core.database.mysql.ConnectorMysql;
import comiot.core.device.Device;
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home.do")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private User user = null;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(SessionValidator.isSessionValid(request, response)) {
			refreshData(request, response);
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		}
		else {
			request.setAttribute("errorMessage", "Invalid Credentials");
			request.getRequestDispatcher("/login.do").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	
	
	private void refreshData(HttpServletRequest request, HttpServletResponse response) {
		/* Checks if the user was not created */
		Connection conn = ConnectorMysql.getConnection();
		int userPk = -1;
		if(user == null) {
			String username = (String) request.getSession().getAttribute("username");
			String password = (String) request.getSession().getAttribute("password");
			if((username != null) && (password != null)) {
				try {
					userPk = DBConnector.userGetPk(conn, username, password);
					if(userPk > 0) {
						user = DBConnector.userGetByPk(conn, userPk);
						userPk = user.getPk();
					}
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			userPk = user.getPk();
		}
		if(userPk > 0){
			DeviceServer deviceServer = new DeviceServer();
			/* Refresh Places */
			List<Place> places = null;
			List<Device> devices = null;
			try {
				places = DBConnector.placesGetByUserPk(conn, userPk);
				for (Place place : places) {
					deviceServer.addPlace(place);
				}
				
				devices = DBConnector.devicesGetByUserPk(conn, userPk);
				for (Device device : devices) {
					deviceServer.addDevice(device);
				}
				
				request.setAttribute("places", places);
				request.setAttribute("devices", devices);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void refreshBusisnessLogic(HttpServletRequest request, HttpServletResponse response) {
		AppConnection appConnection = null;
		DeviceServer deviceServer = null;
		/* Checks if the user was not created */
		Connection conn = ConnectorMysql.getConnection();
		int userPk = -1;
		if(user == null) {
			String username = (String) request.getSession().getAttribute("username");
			String password = (String) request.getSession().getAttribute("password");
			if((username != null) && (password != null)) {
				try {
					userPk = DBConnector.userGetPk(conn, username, password);
					if(userPk > 0) {
						user = DBConnector.userGetByPk(conn, userPk);
						deviceServer = new DeviceServer();
					}
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(user != null){
			userPk = user.getPk();
			if(appConnection == null){
				MqttConnectionConfiguration mqttConfig;
				try {
					mqttConfig = DBConnector.mqttConfigGetByUserPk(conn, userPk);
					if(mqttConfig != null) {
						user.setMqttConfig(mqttConfig);
						appConnection = new AppConnection(mqttConfig);
						deviceServer.setAppConnection(appConnection);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(user.getMqttConfig() == null) {
					request.setAttribute("errorMessage", "You should configure a connection.");
				}
			}
			if(appConnection != null) {
				/* Refresh Places */
				List<Place> places = null;
				List<Device> devices = null;
				try {
					places = DBConnector.placesGetByUserPk(conn, userPk);
					for (Place place : places) {
						deviceServer.addPlace(place);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				/* Refresh Devices */
				try {
					devices = DBConnector.devicesGetByUserPk(conn, userPk);
					for (Device device : devices) {
						deviceServer.addDevice(device);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				request.setAttribute("places", places);
				request.setAttribute("devices", devices);
			}
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
