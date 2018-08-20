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
 * Servlet implementation class CreateUserServlet
 */
@WebServlet("/signup.do")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/signup").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		Connection conn = ConnectorMysql.getConnection();
		
		User user = null;
		try {
			user = DBConnector.userGetByUsername(conn, username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (user != null) {
			request.setAttribute("errorMessage", "This user is already been registered");
			request.getRequestDispatcher("/signup").forward(request, response);
		}
		else {
			String password = request.getParameter("password");
			String confirm = request.getParameter("confirm");
			
			if(password.equals(confirm)) {
				String email = request.getParameter("email");
				user = new User(username, password, email);
				
				try {
					int result = DBConnector.userInsert(conn, user);
					if(result > 0) {
						request.setAttribute("successMessage", "User created. Please login");
						request.getRequestDispatcher("/signup").forward(request, response);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else
			{
				request.setAttribute("errorMessage", "Passwords are not equals");
				request.getRequestDispatcher("/signup").forward(request, response);
			}
		}
	}
}
