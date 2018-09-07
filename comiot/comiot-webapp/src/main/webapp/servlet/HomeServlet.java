package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comiot.core.application.server.DeviceServer;
import comiot.core.application.server.Place;
import comiot.core.application.server.User;
import comiot.core.database.mysql.ConnectorMysql;
import comiot.core.device.Device;

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
			request.getRequestDispatcher("/login").forward(request, response);
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
				
				request.setAttribute("places", places);
				request.setAttribute("devices", devices);
		}
	}
	
	
	private void refreshBusisnessLogic(HttpServletRequest request, HttpServletResponse response) {
	}
}
