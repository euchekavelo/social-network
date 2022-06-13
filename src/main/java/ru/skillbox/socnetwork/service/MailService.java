package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.logging.DebugLogs;

import java.util.Properties;

@Service
@RequiredArgsConstructor
@DebugLogs
public class MailService {

//  @Value("${skillbox.app.mail.user}")
//  private String username;
//  @Value("${skillbox.app.mail.password}")
//  private String password;

  private final String username = "skillboxsocnetwork@gmail.com";
  private final String password = "newpassword123";

  private JavaMailSender getMailSender(){
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    Properties properties = mailSender.getJavaMailProperties();
    properties.put("mail.transport.protocol", "smtp");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.debug", "true");

    return mailSender;
  }


  public void send(String email, String subject, String text) {
    JavaMailSender mailSender = getMailSender();
    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom(username);
    message.setTo(email);
    message.setSubject(subject);
    message.setText(text);

    mailSender.send(message);
  }
}
