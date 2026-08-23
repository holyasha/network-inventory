package com.network.inventory.api_gateway.filter;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.network.inventory.api_gateway.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(request.getRequestURI().equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = extractTokenFromCookie(request);

        if(token == null || !jwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("appication/json");
            response.getWriter().write("{\"error\":\"Unauthorized\"}");
            return;
        }

        String login = jwtUtil.extractLogin(token);
        request.setAttribute("X-User-Login", login);
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        if(request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(coockie -> "JWT_TOKEN".equals(coockie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    
}
