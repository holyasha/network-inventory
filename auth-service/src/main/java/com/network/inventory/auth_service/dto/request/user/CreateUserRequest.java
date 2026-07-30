package com.network.inventory.auth_service.dto.request.user;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotBlank(message = "Логин обязателен")
    String login,

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    String password,

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    String email,

    @NotNull(message = "Роли обязательны")
    Set<Long> roleIds,

    Boolean enabled
) {}
