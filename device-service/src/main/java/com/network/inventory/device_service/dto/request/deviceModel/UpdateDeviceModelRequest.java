package com.network.inventory.device_service.dto.request.deviceModel;

public record UpdateDeviceModelRequest(
    Long manufacturerId,

    Long deviceTypeId,

    String model,

    String description,

    Integer portsCount,

    String managementType
) {}
