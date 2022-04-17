package ru.skillbox.socnetwork.service;

import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;
import javax.mail.*;

@Service
public class MailService {

  private static Properties properties = new Properties();
  static{
    properties.put("mail.smtp.auth", true);
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
  }

  public void send(String text){
    Session session = Session.getInstance(properties, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("kolomarat@gmail.com", "sigmbdeu23");
      }
    });
    Message message = new MimeMessage(session);
    try {
      message.setFrom(new InternetAddress("from@gmail.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("aieya.mail@gmail.com"));
      message.setSubject("Mail Subject");
      String msg = "This is my first email using JavaMailer";
      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(mimeBodyPart);
      message.setContent(multipart);
      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
