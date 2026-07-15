package com.network.inventory.location_service.dto.response;

import java.time.LocalDate;

public record DeviceLocationResponse(
    
    Long id,
    
    Long deviceId,

    RackPositionInfo rackPosition,

    LocalDate installedAt
) {
    public record RackPositionInfo(
        Long id,
        RackInfo rack,
        Integer positionU,
        Boolean occupied
    ) {
    public record RackInfo(
        Long id,
        Long roomId,
        String code
    ) {}
    }
}
