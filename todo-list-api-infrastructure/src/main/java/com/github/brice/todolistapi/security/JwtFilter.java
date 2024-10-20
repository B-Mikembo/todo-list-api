package com.github.brice.todolistapi.security;

import com.github.brice.todolistapi.application.in.HandlingToken;
import com.github.brice.todolistapi.application.token.Token;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private UserDetailsService userDetailsService;
    private HandlingToken handlingToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        Token domainToken = null;
        String email = null;
        boolean isTokenExpired = true;

        var authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            domainToken = handlingToken.getTokenByValue(token);
            isTokenExpired = handlingToken.isTokenExpired(token);
            email = handlingToken.extractEmail(token);
        }

        if (!isTokenExpired && domainToken.user().email().equals(email) && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(email);
            var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
