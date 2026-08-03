package com.network.inventory.auth_service.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
    Long id,
    String login,
    String email,
    Boolean enabled,
    Set<RoleInfo> roles,
    LocalDateTime createdAt
) {
    public record RoleInfo(
        Long id,
        String name
    ) {}
}