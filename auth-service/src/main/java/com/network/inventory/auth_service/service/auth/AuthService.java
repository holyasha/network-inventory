package com.network.inventory.auth_service.service.auth;

import com.network.inventory.auth_service.dto.request.LoginRequest;

public interface AuthService {
    String login(LoginRequest request);
}
