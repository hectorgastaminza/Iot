package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.common.AppConnection;
import application.server.DeviceServer;
import application.server.User;
import database.DBConnector;
import database.mysql.ConnectorMysql;
import protocol.mqtt.MqttConnectionConfiguration;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home.do")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private User user = null;
	private AppConnection appConnection = null;
	private DeviceServer deviceServer = null;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(SessionValidator.isSessionValid(request, response)) {
			refreshBusisnessLogic(request, response);
			request.getRequestDispatcher("/home").forward(request, response);
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
	
	
	private void refreshBusisnessLogic(HttpServletRequest request, HttpServletResponse response) {
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
				
				/* Refresh Devices */
			}
		}
	}
}
