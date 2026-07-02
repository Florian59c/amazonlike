package com.amazonlike.back.mail.template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.amazonlike.back.mail.model.EmailTemplate;

@Service
public class ResetPasswordTemplateBuilder {

  private final TemplateEngine templateEngine;

  @Value("${app.frontend.url}")
  private String frontendUrl;

  public ResetPasswordTemplateBuilder(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  public EmailTemplate build(String token) {

    Context context = new Context();

    String resetUrl = frontendUrl + "/reset-password?token=" + token;

    context.setVariable("resetUrl", resetUrl);

    String html = templateEngine.process(
        "mail/reset-password",
        context);

    return new EmailTemplate(
        "Réinitialisation du mot de passe",
        html);
  }
}
