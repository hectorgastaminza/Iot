package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.server.User;
import database.DBConnector;
import database.mysql.ConnectorMysql;

/**
 * Servlet implementation class UserConfigServlet
 */
@WebServlet("/userconfig.do")
public class UserConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(SessionValidator.isUserValid(request, response)) {
			String username = (String) request.getSession().getAttribute("username");
			User user = null;
			try {
				user = DBConnector.userGetByUsername(ConnectorMysql.getConnection(), username);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("username", user.getUsername());
			request.setAttribute("email", user.getEmail());
			request.getRequestDispatcher("/userconfig").forward(request, response);
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
		String password = request.getParameter("password");
		String confirm = request.getParameter("confirm");

		if(password.equals(confirm)) {
			Connection conn = ConnectorMysql.getConnection();
			String oldUsername = (String) request.getSession().getAttribute("username");
			String oldPassword = (String) request.getSession().getAttribute("password");
			int pk = -1;
			try {
				pk = DBConnector.userGetPk(conn, oldUsername, oldPassword);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(pk > 0) {
				String newUsername = request.getParameter("username");
				String newEmail = request.getParameter("email");
				User user = new User(newUsername, password, newEmail);
				
				int result = -1;
				try {
					result = DBConnector.userUpdate(conn, pk, user);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				if(result > 0) {
					request.setAttribute("successMessage", "User data modified.");
				}
				else {
					request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
				}
			}
			else
			{
				request.setAttribute("errorMessage", "Error. Invalid data has been entered.");
			}
		}
		else
		{
			request.setAttribute("errorMessage", "Passwords are not equals");
		}
			
		request.getRequestDispatcher("/userconfig").forward(request, response);
	}

}
