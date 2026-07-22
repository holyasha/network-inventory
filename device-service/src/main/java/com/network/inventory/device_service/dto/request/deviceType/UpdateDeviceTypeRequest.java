package com.network.inventory.device_service.dto.request.deviceType;

public record UpdateDeviceTypeRequest(
    String name,
    String description
) {}
