package com.network.inventory.device_service.dto.request.deviceModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDeviceModelRequest (
    
    @NotNull(message = "Номер производителя обазтелен!")
    Long manufacturerId,

    @NotNull(message = "Номер типа устройства обязателен!")
    Long deviceTypeId,

    @NotBlank(message = "Модель устройства обязательно!")
    String model,

    String description,

    Integer portsCount,

    String managementType


) {
    
}
