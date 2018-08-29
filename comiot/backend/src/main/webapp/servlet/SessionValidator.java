package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.DBConnector;
import database.mysql.ConnectorMysql;

public class SessionValidator {
       
	public static boolean isSessionValid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean valid = false;
		
		String validsession = (String)request.getSession().getAttribute("validsession");
		
		if((validsession != null) && (validsession.equals(request.getSession().getId()))) {
			valid = true;
		}
		else {
			String username = (String) request.getSession().getAttribute("username");
			String password = (String) request.getSession().getAttribute("password");
			
			if((username != null) && (password != null)) {
				try {
					Connection conn = ConnectorMysql.getConnection();
					int result = DBConnector.userGetPk(conn, username, password);
					conn.close();	
					
					if(result > 0) {
						valid = true;
						request.getSession().setAttribute("userpk", result);
						request.getSession().setAttribute("validsession", request.getSession().getId());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				request.getSession().removeAttribute("validsession");
				request.getSession().removeAttribute("username");
				request.getSession().removeAttribute("password");
			}
		}
		
		return valid;
	}
}
