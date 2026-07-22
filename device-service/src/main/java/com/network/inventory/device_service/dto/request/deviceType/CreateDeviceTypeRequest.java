package com.network.inventory.device_service.dto.request.deviceType;

import jakarta.validation.constraints.NotBlank;

public record CreateDeviceTypeRequest(

    @NotBlank(message = "Название типа устройства обязательно!")
    String name,

    String description

) {}
