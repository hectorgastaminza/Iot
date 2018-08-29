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

import application.common.AppConnection;
import database.DBConnector;
import database.mysql.ConnectorMysql;
import device.Device;
import device.command.eDeviceCommands;
import protocol.mqtt.MqttConnectionConfiguration;

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
		
		request.getRequestDispatcher("/home.do").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	private void sendAppCommand(int userPk, int deviceId, eDeviceCommands command) {
		AppConnection appConnection = getAppConnection(userPk);
		if(appConnection != null) {
			appConnection.connect();
			appConnection.commandRequest(1, deviceId, command, 0);
			appConnection.disconnect();
		}
	}
	
	private AppConnection getAppConnection(int userPk) {
		AppConnection appConnection = null;
		
		try {
			MqttConnectionConfiguration mqttConfig = null;
			Connection conn = ConnectorMysql.getConnection();
			mqttConfig = DBConnector.mqttConfigGetByUserPk(conn, userPk);
			conn.close();
			
			if(mqttConfig != null) {
				appConnection = new AppConnection(mqttConfig);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return appConnection;
	}
	
}
