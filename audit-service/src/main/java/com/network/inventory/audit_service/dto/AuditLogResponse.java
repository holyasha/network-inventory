package com.network.inventory.audit_service.dto;

import java.time.LocalDateTime;

public record AuditLogResponse(
    Long id,
    String serviceName,
    String entityName,
    Long entityId,
    String action,
    String userLogin,
    String oldValue,
    String newValue,
    LocalDateTime createdAt
) {}
