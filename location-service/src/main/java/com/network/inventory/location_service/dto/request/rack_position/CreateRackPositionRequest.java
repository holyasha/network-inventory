package com.network.inventory.location_service.dto.request.rack_position;

import jakarta.validation.constraints.NotNull;

public record CreateRackPositionRequest(
    @NotNull(message = "Номер стойки обязателен!")
    Long rackId,

    @NotNull(message = "Позиция обязательна!")
    Integer positionU,

    Boolean occupied
) {}
