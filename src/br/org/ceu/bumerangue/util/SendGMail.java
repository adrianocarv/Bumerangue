package br.org.ceu.bumerangue.util;
import java.io.FileInputStream;
import java.security.Security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendGMail {

	private static Properties config = new Properties();;
	
	{
		try{
			config.load(new FileInputStream("config.properties"));
		}catch (Exception e) {
			try {
				config.load(new FileInputStream(System.getProperty("user.dir")+"/src/br/org/ceu/bumerangue/util/config.properties"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) throws Exception {

		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		new SendGMail().sendSSLMessage();
		System.out.println("Sucessfully Sent mail to All Users");
	}

	public void sendSSLMessage() throws MessagingException {
		boolean debug = true;

		Properties props = new Properties();
		props.put("mail.smtp.host", config.getProperty("mail.host"));
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", config.getProperty("mail.port"));
		props.put("mail.smtp.socketFactory.port", config.getProperty("mail.port"));
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(config.getProperty("mail.username"),config.getProperty("mail.password"));
					}
				});

		session.setDebug(debug);

		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(config.getProperty("mail.from"));
		msg.setFrom(addressFrom);

		String[] sendTo = { config.getProperty("mail.to") };
		InternetAddress[] addressTo = new InternetAddress[sendTo.length];
		for (int i = 0; i < sendTo.length; i++) {
			addressTo[i] = new InternetAddress(sendTo[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(config.getProperty("mail.subject"));
		msg.setContent(config.getProperty("mail.body"), "text/plain");
		Transport.send(msg);
	}
}