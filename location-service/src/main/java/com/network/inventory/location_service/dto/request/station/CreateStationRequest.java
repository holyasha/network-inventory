package com.network.inventory.location_service.dto.request.station;

import jakarta.validation.constraints.NotBlank;

public record CreateStationRequest(

    @NotBlank(message = "Название станции обязательно!")
    String name,

    @NotBlank(message = "Линия станции обязательна!")
    String line,

    String address,

    String description
) {}
