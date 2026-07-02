package com.amazonlike.back.mail.sender;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SmtpMailSender {

  private final JavaMailSender mailSender;

  public SmtpMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void send(
      String to,
      String subject,
      String html) throws MessagingException {

    MimeMessage message = mailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(html, true);
    helper.setFrom("no-reply@amazonLike.com");

    mailSender.send(message);
  }
}