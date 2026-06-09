package com.amazonlike.back.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cookie")
public class CookieProperties {

  private String name;
  private boolean secure;
  private int maxAge;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isSecure() {
    return secure;
  }

  public void setSecure(boolean secure) {
    this.secure = secure;
  }

  public int getMaxAge() {
    return maxAge;
  }

  public void setMaxAge(int maxAge) {
    this.maxAge = maxAge;
  }
}