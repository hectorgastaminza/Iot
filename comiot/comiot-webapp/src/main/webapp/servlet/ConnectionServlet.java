package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comiot.core.database.DBConnector;
import comiot.core.database.mysql.ConnectorMysql;
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
		if(SessionValidator.isSessionValid(request, response)) {
			int userPk = (int) request.getSession().getAttribute("userpk");
			MqttConnectionConfiguration mqttConfig = null;
			try {
				Connection conn = ConnectorMysql.getConnection();
				mqttConfig = DBConnector.mqttConfigGetByUserPk(conn, userPk);
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(mqttConfig != null) {
				request.setAttribute("mqtthost", mqttConfig.getBrokerHost());
				request.setAttribute("mqttport", mqttConfig.getBrokerPort());
				request.setAttribute("mqttusername", mqttConfig.getUsername());
				request.setAttribute("mqttpassword", mqttConfig.getPasswordStr());
				request.setAttribute("mqtttopic", mqttConfig.getRootTopic());
			}
			request.getRequestDispatcher("/connection").forward(request, response);
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
		String mqtthost = request.getParameter("mqtthost");
		String mqttport = request.getParameter("mqttport");
		String mqttusername = request.getParameter("mqttusername");
		String mqttpassword = request.getParameter("mqttpassword");
		String mqtttopic = request.getParameter("mqtttopic");
		int userPk = (int)request.getSession().getAttribute("userpk");
		
		if(mqttport != null)
		{
			int port = Integer.parseInt(mqttport);
			
			MqttConnectionConfiguration mqttConfig = new MqttConnectionConfiguration(mqtthost, 
					port, 
					mqttusername, 
					mqttpassword, 
					mqtttopic);
			
			int result = -1;
			try {
				Connection conn = ConnectorMysql.getConnection();
				result = DBConnector.mqttConfigRefresh(conn, userPk, mqttConfig);
				conn.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if(result > 0) {
				request.setAttribute("successMessage", "Connection data modified.");
				response.sendRedirect("/home");
			}
			else {
				request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
				request.getRequestDispatcher("/connection").forward(request, response);
			}
		}
		else
		{
			request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
			request.getRequestDispatcher("/connection").forward(request, response);
		}
	}

}
