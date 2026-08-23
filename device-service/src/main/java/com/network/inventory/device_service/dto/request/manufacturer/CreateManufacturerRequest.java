package com.network.inventory.device_service.dto.request.manufacturer;

import jakarta.validation.constraints.NotBlank;

public record CreateManufacturerRequest(

    @NotBlank(message = "Название производителя обязательно!")
    String name,

    @NotBlank(message = "Страна производителя обязательна!")
    String country,

    String website
) {

}
