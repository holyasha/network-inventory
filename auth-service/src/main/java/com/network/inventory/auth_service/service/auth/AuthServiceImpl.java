package com.network.inventory.auth_service.service.auth;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.network.inventory.auth_service.dto.request.LoginRequest;
import com.network.inventory.auth_service.entity.User;
import com.network.inventory.auth_service.exeption.ResourceNotFoundException;
import com.network.inventory.auth_service.repository.UserRepository;
import com.network.inventory.auth_service.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByLogin(request.login())
            .orElseThrow(() -> new ResourceNotFoundException("Неверынй логин или пароль"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResourceNotFoundException("Неверный логин или пароль");
        }

        if (!user.getEnabled()) {
            throw new ResourceNotFoundException("Пользователь отключен");
        }

        List<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getName())
                .toList();

        return jwtUtil.generateToken(user.getLogin(), roles);
    }

}
