package com.network.inventory.location_service.dto.response;

public record StationResponse(
    Long id,

    String name,

    String line,

    String address,

    String description

) {}
