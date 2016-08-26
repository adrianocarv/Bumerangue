package br.org.ceu.bumerangue.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

public class MMSimpleMailMessage extends SimpleMailMessage {
	
	private String textPlain;
	private String htmlPlain;
    private String template;
    private Map params;
	private boolean htmlMessage = false;
	
	public MMSimpleMailMessage() { }
	public MMSimpleMailMessage(boolean htmlMessage) {
		this.htmlMessage = htmlMessage;
	}

	public MMSimpleMailMessage(boolean htmlMessage, String from, String[] to, String subject, String text) {
		this.htmlMessage = htmlMessage;
		this.setFrom(from);
		this.setTo(to);
		this.setSubject(subject);
		this.setText(text);
	}

	public boolean isHtmlMessage() {
		return htmlMessage;
	}
	public void setHtmlMessage(boolean htmlMessage) {
		this.htmlMessage = htmlMessage;
	}
	public String getHtmlPlain() {
		return htmlPlain;
	}
	public void setHtmlPlain(String htmlPlain) {
		this.htmlPlain = htmlPlain;
	}
	public String getTextPlain() {
		return textPlain;
	}
	public void setTextPlain(String textPlain) {
		this.textPlain = textPlain;
    }
    public void setParameters(Map params) {
        this.params = params;
    }
    public Map getParameters(){
        return params;
    }
    public void setTemplate(String template) {
        this.template = template;
    }
    public String getTemplate(){
        return template;
    }

    public static void main(String[] args) throws Exception{
		new MMSimpleMailMessage().sendMail();
	}
    
    public void sendMail() throws MessagingException
    {
    System.out.println("sendMail()...");
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "465");

//     props.put("mail.smtp.auth", "true");

    Session session = Session.getDefaultInstance(props, null);
    session.setDebug(false);
    MimeMessage message = new MimeMessage(session);
    InternetAddress addressFrom = new InternetAddress("adrianocarv@yahho.com.br");
    message.setFrom(addressFrom);

    InternetAddress addressTo = new InternetAddress("adrianocarv@yahho.com.br");
    message.setFrom(addressTo);
    message.addRecipient(Message.RecipientType.TO, addressTo);

    System.out.println("sendMail() 2...");

    message.setSubject("assunto");
    message.setContent("texto", "text/plain");

    Transport transport = session.getTransport("smtp");

//    ***** GET STUCK HERE!!! ******
    transport.connect("smtp.gmail.com", 587, "adrianocarv@gmail.com", "290980");

    System.out.println("sendMail() 3...");

    Transport.send(message);

    
    System.out.println("sendMail() 4...");

    }    
}
