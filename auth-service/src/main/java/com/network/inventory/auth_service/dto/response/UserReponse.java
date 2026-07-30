package com.network.inventory.auth_service.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

public record UserReponse(
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