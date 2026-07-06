package com.amazonlike.back.mail.template;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.amazonlike.back.mail.model.EmailTemplate;

@Service
public class NewEmailConfirmationTemplateBuilder {

  private final TemplateEngine templateEngine;

  public NewEmailConfirmationTemplateBuilder(
      TemplateEngine templateEngine) {

    this.templateEngine = templateEngine;
  }

  public EmailTemplate build() {

    Context context = new Context();

    String html = templateEngine.process(
        "mail/new-email-confirmation",
        context);

    return new EmailTemplate(
        "Votre adresse e-mail a été mise à jour",
        html);
  }
}