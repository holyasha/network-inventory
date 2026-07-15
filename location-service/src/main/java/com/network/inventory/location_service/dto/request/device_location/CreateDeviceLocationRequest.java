package com.network.inventory.location_service.dto.request.device_location;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record CreateDeviceLocationRequest(
    
    @NotNull(message = "Id устройства обязателен!")
    Long deviceId,

    @NotNull(message = "Id позиции стойки обязателен!")
    Long rackPositionId,

    LocalDate installedAt
) {}
