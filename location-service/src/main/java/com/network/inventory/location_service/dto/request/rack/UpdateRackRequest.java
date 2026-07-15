package com.network.inventory.location_service.dto.request.rack;

public record UpdateRackRequest(
    Long roomId,

    String code,

    Integer height,

    String manufacturer,

    String description
) {
    
}
