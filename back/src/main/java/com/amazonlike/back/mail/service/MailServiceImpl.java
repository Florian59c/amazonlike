package com.amazonlike.back.mail.service;

import org.springframework.stereotype.Service;

import com.amazonlike.back.mail.model.EmailTemplate;
import com.amazonlike.back.mail.sender.SmtpMailSender;
import com.amazonlike.back.mail.template.ResetPasswordTemplateBuilder;
import com.amazonlike.back.mail.template.UpdateEmailTemplateBuilder;

@Service
public class MailServiceImpl implements MailService {

  private final ResetPasswordTemplateBuilder templateBuilder;
  private final SmtpMailSender smtpMailSender;
  private final UpdateEmailTemplateBuilder updateEmailTemplateBuilder;

  public MailServiceImpl(
      ResetPasswordTemplateBuilder templateBuilder,
      SmtpMailSender smtpMailSender,
      UpdateEmailTemplateBuilder updateEmailTemplateBuilder) {
    this.templateBuilder = templateBuilder;
    this.smtpMailSender = smtpMailSender;
    this.updateEmailTemplateBuilder = updateEmailTemplateBuilder;
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
}