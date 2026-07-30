package com.network.inventory.auth_service.dto.request.user;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
    String login,
    @Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    String password,

    @Email(message = "Некорректный email")
    String email,

    Set<Long> roleIds,
    Boolean enabled
) {}
