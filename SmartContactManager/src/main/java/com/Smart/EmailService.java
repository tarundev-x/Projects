package com.Smart;



import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	
	public boolean sendEmail(String message, String subject, String to) {
		
		
		boolean flag=false;
		String from = "tarundev2000x@gmail.com";
		
		
		// variable for gmail
		String host = "smtp.gmail.com";

		// get the system properties
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES " + properties);

		// setting important info to properties object

		// host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		// step 1: to get the session object
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("tarundev2000x@gmail.com", "tqdmeigpnvimukjp");
			}

		});
		// step 2: compose the message

		session.setDebug(true);
		MimeMessage m = new MimeMessage(session);
		// from
		try {
			 m.setFrom(new InternetAddress(from));

	            // Adding recipient
	            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	            // Adding subject to message
	            m.setSubject(subject);

	            // Adding text to message
	            m.setContent(message, "text/html; charset=utf-8");

	            // Step 3: send message using transport class
	            Transport.send(m);

			System.out.println("Sent successfully.........");
			flag=true;

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error sending email: " + e.getMessage());
		}
		return flag;
	}



}

