package com.network.inventory.device_service.dto.request.deviceStatus;

import jakarta.validation.constraints.NotBlank;

public record CreateDeviceStatusRequest(

    @NotBlank(message = "Наименование статуса обязательно!")
    String name,

    String color
) {}