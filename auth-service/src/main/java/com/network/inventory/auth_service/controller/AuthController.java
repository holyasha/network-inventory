package com.network.inventory.auth_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.network.inventory.auth_service.dto.request.LoginRequest;
import com.network.inventory.auth_service.service.auth.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Value("${jwt.cookie.max-age}")
    private int cookieMaxAge;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
        @Valid @RequestBody LoginRequest request,
        HttpServletResponse response
    ) {
        String token = authService.login(request);

        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); //замена
        cookie.setPath("/");
        cookie.setMaxAge(cookieMaxAge);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

}
