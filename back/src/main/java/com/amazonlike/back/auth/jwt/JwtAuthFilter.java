package com.amazonlike.back.auth.jwt;

import com.amazonlike.back.config.CookieProperties;
import com.amazonlike.back.user.entity.User;
import com.amazonlike.back.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final CookieProperties cookieProperties;

  public JwtAuthFilter(
      JwtService jwtService,
      UserRepository userRepository,
      CookieProperties cookieProperties) {

    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.cookieProperties = cookieProperties;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    String path = request.getServletPath();

    // ignore les routes auth
    if (path.startsWith("/auth/") || path.startsWith("/test/")) {
      filterChain.doFilter(request, response);
      return;
    }

    Cookie[] cookies = request.getCookies();

    if (cookies == null) {
      filterChain.doFilter(request, response);
      return;
    }

    String cookieName = cookieProperties.getName();

    String token = Arrays.stream(cookies)
        .filter(cookie -> cookieName.equals(cookie.getName()))
        .findFirst()
        .map(Cookie::getValue)
        .orElse(null);

    // token absent
    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    // token invalide
    if (!jwtService.isTokenValid(token)) {
      SecurityContextHolder.clearContext();

      filterChain.doFilter(request, response);
      return;
    }

    String userId = jwtService.extractUserId(token);
    String tokenVersion = jwtService.extractTokenVersion(token);

    User user = userRepository.findById(UUID.fromString(userId))
        .orElse(null);

    // vérifications sécurité
    if (user == null
        || user.getTokenVersion() == null
        || !user.getTokenVersion().toString().equals(tokenVersion)
        || !user.isEnabled()
        || user.isLocked()
        || user.getDeletedAt() != null) {

      SecurityContextHolder.clearContext();

      filterChain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.authenticated(
        user,
        null,
        List.of());

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }
}