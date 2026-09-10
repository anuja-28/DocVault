package com.acube.docvault.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

System.out.println("AUTH HEADERrr = " + authHeader);

if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    System.out.println("NO BEARER TOKEN");
    filterChain.doFilter(request, response);

    return;
}

String token = authHeader.substring(7);

System.out.println("TOKEN RECEIVED");

if (jwtService.isTokenValid(token)) {

    System.out.println("TOKEN VALID");

    String email = jwtService.extractEmail(token);

    System.out.println("EMAIL = " + email);

    UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    Collections.emptyList()
            );

    SecurityContextHolder
            .getContext()
            .setAuthentication(authentication);

    System.out.println("AUTHENTICATION SET = "
            + SecurityContextHolder.getContext().getAuthentication());
    
} else {
    System.out.println("TOKEN INVALIlD");
}

filterChain.doFilter(request, response);
    }
}