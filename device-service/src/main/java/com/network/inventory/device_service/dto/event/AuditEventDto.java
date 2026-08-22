package com.network.inventory.device_service.dto.event;

import java.time.LocalDateTime;

public record AuditEventDto(
    String serviceName,
    String entityName,
    Long entityId,
    String action,
    String userLogin,
    String oldValue,
    String newValue,
    LocalDateTime timestamp
) {
    public AuditEventDto(String serviceName, String entityName, Long entityId, String action, String userLogin) {
        this(serviceName, entityName, entityId, action, userLogin, null, null, LocalDateTime.now());
    }
}
