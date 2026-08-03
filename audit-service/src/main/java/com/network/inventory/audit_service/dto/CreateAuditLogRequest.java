package com.network.inventory.audit_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAuditLogRequest(

    @NotBlank(message = "Название сервиса обязательно")
    String serviceName,

    @NotBlank(message = "Название сущности обязательно")
    String entityName,

    @NotNull(message = "ID сущности обязателен")
    Long entityId,

    @NotBlank(message = "Действие обязательно")
    String action,

    String userLogin,
    String oldValue,
    String newValue
  ) {}
