package com.network.inventory.location_service.dto.request.room;

import jakarta.validation.constraints.NotBlank;

public record UpdateRoomRequest(

    Long stationId,

    @NotBlank(message = "Название помещения обязательно!")
    String name,

    @NotBlank(message = "Тип помещения обязателен!")
    String type,

    Integer floor,

    String description
){}
