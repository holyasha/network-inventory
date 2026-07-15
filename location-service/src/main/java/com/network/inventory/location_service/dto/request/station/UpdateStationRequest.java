package com.network.inventory.location_service.dto.request.station;

public record UpdateStationRequest(
    String name,

    String line,

    String address,

    String description
) {}
