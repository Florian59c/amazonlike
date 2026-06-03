package com.amazonlike.back.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import com.amazonlike.back.config.JwtProperties;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

  private final JwtProperties jwtProperties;

  public JwtService(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(
        jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
  }

  private Claims getClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String generateToken(UUID userId, UUID tokenVersion) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + jwtProperties.getExpirationMs());

    return Jwts.builder()
        .setSubject(userId.toString())
        .claim("tokenVersion", tokenVersion.toString())
        .setIssuedAt(now)
        .setExpiration(expiry)
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractUserId(String token) {
    return getClaims(token).getSubject();
  }

  public String extractTokenVersion(String token) {
    return getClaims(token).get("tokenVersion", String.class);
  }

  public boolean isTokenValid(String token) {
    try {
      getClaims(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}