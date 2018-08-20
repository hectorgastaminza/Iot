package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.DBConnector;
import database.mysql.ConnectorMysql;

public class SessionValidator {
       
	public static boolean isUserValid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean valid = false;
		String username = (String) request.getSession().getAttribute("username");
		String password = (String) request.getSession().getAttribute("password");
		
		if((username != null) && (password != null)) {
			try {
				int result = DBConnector.userGetPk(ConnectorMysql.getConnection(), username, password);
				if(result > 0) {
					valid = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return valid;
	}
}
