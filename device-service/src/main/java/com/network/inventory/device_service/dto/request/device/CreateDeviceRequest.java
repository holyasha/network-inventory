package com.network.inventory.device_service.dto.request.device;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDeviceRequest(

    @NotBlank(message = "Инвентарный номер обязателен!")
    String inventoryNumber,

    @NotBlank(message = "Серийный номер обязателен!")
    String serialNumber,

    @NotNull(message = "Номер модели устройства обязателен!")
    Long deviceModelId,

    @NotNull(message = "Номер статуса обязателен!")
    Long statusId,

    LocalDate purchaseDate,

    LocalDate installationDate,

    LocalDate warrantyUntil,

    String comment
) {}
