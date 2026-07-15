package com.network.inventory.location_service.dto.request.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRoomRequest(
    @NotNull(message = "Id станции обязателен!")
    Long stationId,

    @NotBlank(message = "Название помещения обязательно!")
    String name,

    @NotBlank(message = "Тип помещения обязателен!")
    String type,

    Integer floor,

    String description
    
) {}
