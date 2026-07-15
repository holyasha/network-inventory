package com.network.inventory.location_service.dto.request.device_location;

import java.time.LocalDate;

public record UpdateDeviceLocationRequest(
    
    Long deviceId,

    Long rackPositionId,

    LocalDate installedAt
) {}
