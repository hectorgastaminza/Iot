package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DBConnector;
import database.mysql.ConnectorMysql;

@WebServlet(urlPatterns="/login.do")
public class LoginServlet extends HttpServlet {
	
	//private UserValidationService userValidationService = new UserValidationService();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/login").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
	    HttpSession session = request.getSession();
	    session.setAttribute("username", "");
	    session.setAttribute("password", "");
		
		int userID = 0;
		try {
			userID = DBConnector.userGetPk(ConnectorMysql.getConnection(), username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (userID > 0) {
		    session.setAttribute("username", username);
		    session.setAttribute("password", password);
			response.sendRedirect("/home.do");
		}
		else {
			request.setAttribute("errorMessage", "Invalid Credentials");
			request.getRequestDispatcher("/login").forward(request, response);
		}
	}
}