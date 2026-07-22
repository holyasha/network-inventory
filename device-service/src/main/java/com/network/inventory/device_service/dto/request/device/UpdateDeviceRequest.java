package com.network.inventory.device_service.dto.request.device;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record UpdateDeviceRequest(
    
    String inventoryNumber,

    String serialNumber,

    Long deviceModelId,

    @NotNull(message = "Номер статуса обязателен!")
    Long statusId,

    LocalDate purchaseDate,
    
    LocalDate installationDate,
    
    LocalDate warrantyUntil,
    
    String comment
) {}
