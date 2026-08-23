package com.network.inventory.device_service.dto.response;

import java.time.LocalDateTime;

public record ManufacturerResponse(

    Long id,

    String name,

    String country,

    String website,

    LocalDateTime updatedAt,

    LocalDateTime createdAt
) {

}
