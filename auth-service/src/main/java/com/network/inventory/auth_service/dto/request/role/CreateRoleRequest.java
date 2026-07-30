package com.network.inventory.auth_service.dto.request.role;

import jakarta.validation.constraints.NotBlank;

public record CreateRoleRequest(
    @NotBlank(message = "Название роли обязательно")
      String name
) {}
