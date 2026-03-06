package com.produc.infraestructure.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterJWT extends OncePerRequestFilter   {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String PRODUCTS_PATH = "/products";

    private final String jwtSecret;

    public FilterJWT(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override 
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith(PRODUCTS_PATH);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            throw new BadCredentialsException("Authorization header must be Bearer token");
        }

        String token = authHeader.substring(BEARER_PREFIX.length()).trim();
        if (token.isBlank()) {
            throw new BadCredentialsException("Bearer token is required");
        }

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String subject = claims.getSubject();
            if (subject == null || subject.isBlank()) {
                throw new BadCredentialsException("Invalid token subject");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(subject, null,
                    List.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            SecurityContextHolder.clearContext();
            throw new BadCredentialsException("Invalid or expired token", exception);
        }
    }
}
