package com.amazonlike.back.mail.model;

public class EmailTemplate {

  private String subject;
  private String htmlContent;

  public EmailTemplate(String subject, String htmlContent) {
    this.subject = subject;
    this.htmlContent = htmlContent;
  }

  public String getSubject() {
    return subject;
  }

  public String getHtmlContent() {
    return htmlContent;
  }
}