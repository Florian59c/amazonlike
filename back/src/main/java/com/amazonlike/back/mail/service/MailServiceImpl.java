package com.amazonlike.back.mail.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonlike.back.mail.model.EmailTemplate;
import com.amazonlike.back.mail.sender.SmtpMailSender;
import com.amazonlike.back.mail.template.NewEmailConfirmationTemplateBuilder;
import com.amazonlike.back.mail.template.OldEmailAlertTemplateBuilder;
import com.amazonlike.back.mail.template.ResetPasswordTemplateBuilder;
import com.amazonlike.back.mail.template.UpdateEmailTemplateBuilder;

import jakarta.mail.MessagingException;

@Service
public class MailServiceImpl implements MailService {

  private final ResetPasswordTemplateBuilder templateBuilder;
  private final SmtpMailSender smtpMailSender;
  private final UpdateEmailTemplateBuilder updateEmailTemplateBuilder;
  private final OldEmailAlertTemplateBuilder oldEmailAlertTemplateBuilder;
  private final NewEmailConfirmationTemplateBuilder newEmailConfirmationTemplateBuilder;
  private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

  public MailServiceImpl(
      ResetPasswordTemplateBuilder templateBuilder,
      SmtpMailSender smtpMailSender,
      UpdateEmailTemplateBuilder updateEmailTemplateBuilder,
      OldEmailAlertTemplateBuilder oldEmailAlertTemplateBuilder,
      NewEmailConfirmationTemplateBuilder newEmailConfirmationTemplateBuilder) {
    this.templateBuilder = templateBuilder;
    this.smtpMailSender = smtpMailSender;
    this.updateEmailTemplateBuilder = updateEmailTemplateBuilder;
    this.oldEmailAlertTemplateBuilder = oldEmailAlertTemplateBuilder;
    this.newEmailConfirmationTemplateBuilder = newEmailConfirmationTemplateBuilder;
  }

  @Override
  public void sendResetPasswordMail(
      String email,
      String token) {

    try {

      EmailTemplate template = templateBuilder.build(token);

      smtpMailSender.send(
          email,
          template.getSubject(),
          template.getHtmlContent());

    } catch (Exception e) {
      throw new RuntimeException("Impossible d'envoyer l'e-mail de réinitialisation du mot de passe", e);
    }
  }

  @Override
  public void sendUpdateEmailMail(String email, String token) {

    try {

      EmailTemplate template = updateEmailTemplateBuilder.build(token);

      smtpMailSender.send(
          email,
          template.getSubject(),
          template.getHtmlContent());

    } catch (Exception e) {

      throw new RuntimeException(
          "Impossible d'envoyer l'e-mail de changement d'adresse e-mail",
          e);
    }
  }

  @Override
  public void sendEmailChangeEmails(String oldEmail, String newEmail) {

    try {
      EmailTemplate alert = oldEmailAlertTemplateBuilder.build();
      smtpMailSender.send(
          oldEmail,
          alert.getSubject(),
          alert.getHtmlContent());

      EmailTemplate success = newEmailConfirmationTemplateBuilder.build();
      smtpMailSender.send(
          newEmail,
          success.getSubject(),
          success.getHtmlContent());

    } catch (Exception e) {
      log.error("EMAIL CHANGE FLOW FAILED (old={}, new={})", oldEmail, newEmail, e);
    }
  }
}