package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService {

  private JavaMailSender mailSender;

  private JavaMailSender getMailSender(){
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    mailSender.setUsername("skillboxsocnetwork@gmail.com");
    mailSender.setPassword("newpassword123");

    Properties properties = mailSender.getJavaMailProperties();
    properties.put("mail.transport.protocol", "smtp");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.debug", "true");

    return mailSender;
  }


  public void send(String email, String subject, String text) {
    mailSender = getMailSender();
    SimpleMailMessage message = new SimpleMailMessage();

    message.setTo(email);
    message.setSubject(subject);
    message.setText(text);

    mailSender.send(message);
  }
}
