package br.org.ceu.bumerangue.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
  
public class SendYahoo {  
  
    /**
     * @param args
     */
    public static void main(String[] args) {  
          
        String user     = "adrianocarv";  
        String password = "290980";
  
        String host     = "smtp.mail.yahoo.com.br";  
        int port        = 587;  
        String from     = "ola@teste.com.br";  
        String to       = "adrianocarv@gmail.com";  
          
        try {  
            Properties props = System.getProperties();  
            props.put("mail.smtp.host", host);  
            props.put("mail.smtp.port", port);  
            props.put("mail.smtp.auth", "true");  
  
            Session session = Session.getDefaultInstance(props, null);  
  
            MimeMessage msg = new MimeMessage(session);  
            msg.setFrom(new InternetAddress(from));  
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));  
            msg.setSentDate(new Date());  
            msg.setSubject("Testando envio de e-mails com JAVA");  
            msg.setText("Se este texto estiver aparecendo é porque fungou! Uhu!");  
            msg.saveChanges();  
  
            Transport transport = session.getTransport("smtp");  
            System.out.println("Tentando conectar ao servidor ...");              
            transport.connect(host, port, user, password);  
            System.out.println("Enviando Mensagem ...");  
            transport.sendMessage(msg, msg.getAllRecipients());  
            transport.close();  
            System.out.println("E-mail enviado com sucesso!");  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
  
}