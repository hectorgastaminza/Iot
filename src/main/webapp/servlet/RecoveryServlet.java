package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.server.User;
import database.DBConnector;
import database.mysql.ConnectorMysql;

/**
 * Servlet implementation class RecoveryServlet
 */
@WebServlet("/recovery.do")
public class RecoveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/recovery").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		
		try {
			User user = null;
			
			Connection conn = ConnectorMysql.getConnection();
			user = DBConnector.userGetByEmail(conn, email);
			conn.close();
			
			if(user != null) {
				boolean result = sendRecoveryEmail(user);
				if(result) {
					request.setAttribute("successMessage", "We've sent to you an email with your credentials.");
					request.getRequestDispatcher("/recovery").forward(request, response);
				}
			}
			else {
				request.setAttribute("errorMessage", "This email has not been registered");
				request.getRequestDispatcher("/recovery").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private boolean sendRecoveryEmail(User user) throws MessagingException {
		boolean retval = false;
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", true);
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.ssl.enable", true);
		
		Session session = Session.getInstance(prop, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication("comiotprojectnoreply@gmail.com", "noreply+1234");
		    }
		});
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("comiotprojectnoreply@gmail.com"));
		message.setRecipients(
				Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
		message.setSubject("COMIOT password recovery");

		String msg = "COMIOT password recovery <br>"
				+ "User: " + user.getUsername() + "<br>"
				+ "Password: " + user.getPassword() + "<br>" + "<br>"
				+ "Thanks for choose COMIOT";

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		message.setContent(multipart);

		Transport.send(message);
		
		retval = true;
		
		return retval;
	}

}
