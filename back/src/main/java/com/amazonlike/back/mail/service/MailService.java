package com.amazonlike.back.mail.service;

public interface MailService {

  void sendResetPasswordMail(
      String email,
      String token);

  void sendUpdateEmailMail(
      String email,
      String token);
}