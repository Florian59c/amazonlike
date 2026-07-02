package com.amazonlike.back.mail.service;

public interface MailService {

  void sendResetPasswordMail(
      String email,
      String token);
}