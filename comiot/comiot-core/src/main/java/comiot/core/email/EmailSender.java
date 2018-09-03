package comiot.core.email;

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

import comiot.core.application.server.User;

public class EmailSender {

	public static boolean sendRecovery(User user) throws MessagingException {
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
		        return new PasswordAuthentication(EMAIL_USER, EMAIL_PASS);
		    }
		});
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(EMAIL_USER));
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
	
	/**
	 * 
	 */
	static private String EMAIL_USER =  "comiotprojectnoreply@gmail.com";
	/**
	 * 
	 */
	static private String EMAIL_PASS =  "noreply+1234";
	/**
	 * 
	 */
}
