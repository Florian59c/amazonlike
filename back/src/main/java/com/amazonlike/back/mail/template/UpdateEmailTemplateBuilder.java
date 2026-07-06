package com.amazonlike.back.mail.template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.amazonlike.back.mail.model.EmailTemplate;

@Service
public class UpdateEmailTemplateBuilder {

  private final TemplateEngine templateEngine;

  @Value("${app.frontend.url}")
  private String frontendUrl;

  public UpdateEmailTemplateBuilder(
      TemplateEngine templateEngine) {

    this.templateEngine = templateEngine;
  }

  public EmailTemplate build(String token) {

    Context context = new Context();

    String confirmUrl = frontendUrl + "/confirm-email-update?token=" + token;

    context.setVariable("confirmUrl", confirmUrl);

    String html = templateEngine.process(
        "mail/update-email",
        context);

    return new EmailTemplate(
        "Confirmation du changement d'adresse e-mail",
        html);
  }
}