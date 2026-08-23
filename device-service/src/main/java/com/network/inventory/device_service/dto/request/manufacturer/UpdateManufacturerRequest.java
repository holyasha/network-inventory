package com.network.inventory.device_service.dto.request.manufacturer;

public record UpdateManufacturerRequest (
    String name,
    String country,
    String website
) {}
