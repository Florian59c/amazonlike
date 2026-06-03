package com.amazonlike.back.auth.jwt;

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

  public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String path = request.getServletPath();

    if (path.startsWith("/auth/")) {
      filterChain.doFilter(request, response);
      return;
    }

    Cookie[] cookies = request.getCookies();

    if (cookies == null) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = Arrays.stream(cookies)
        .filter(c -> "auth_token".equals(c.getName()))
        .findFirst()
        .map(Cookie::getValue)
        .orElse(null);

    if (token == null || !jwtService.isTokenValid(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    String userId = jwtService.extractUserId(token);
    String tokenVersion = jwtService.extractTokenVersion(token);

    User user = userRepository.findById(UUID.fromString(userId)).orElse(null);

    if (user == null ||
        user.getTokenVersion() == null ||
        !user.getTokenVersion().toString().equals(tokenVersion)) {
      filterChain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, List.of());

    SecurityContextHolder.getContext().setAuthentication(auth);

    filterChain.doFilter(request, response);
  }
}