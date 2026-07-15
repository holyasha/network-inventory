package com.network.inventory.location_service.dto.request.rack;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRackRequest(
    @NotNull(message = "Номер помещения обязателен!")
    Long roomId,

    @NotBlank(message = "Код стойки обязателен!")
    String code,

    @NotNull(message = "Высота стойки обязательна!")
    Integer height,

    String manufacturer,

    String description
){}
