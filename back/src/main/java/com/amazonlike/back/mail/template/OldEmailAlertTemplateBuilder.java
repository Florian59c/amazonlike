package com.amazonlike.back.mail.template;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.amazonlike.back.mail.model.EmailTemplate;

@Service
public class OldEmailAlertTemplateBuilder {

  private final TemplateEngine templateEngine;

  public OldEmailAlertTemplateBuilder(
      TemplateEngine templateEngine) {

    this.templateEngine = templateEngine;
  }

  public EmailTemplate build() {

    Context context = new Context();

    String html = templateEngine.process(
        "mail/old-email-alert",
        context);

    return new EmailTemplate(
        "Votre adresse e-mail a été modifiée",
        html);
  }
}