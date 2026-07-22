package com.network.inventory.device_service.dto.response;

import java.time.LocalDateTime;

public record DeviceModelResponse(

    Long id,

    ManufacturerInfo manufacturer,

    DeviceTypeInfo deviceType,

    String model,

    String description,

    Integer portsCount,

    String managementType,

    LocalDateTime createdAt
) {
    public record ManufacturerInfo (
        Long id,
        
        String name,

        String country,

        LocalDateTime updatedAt,

        LocalDateTime createdAt
    ) {}
    public record DeviceTypeInfo (
        Long id,

        String name,

        String description
    ) {}
}
