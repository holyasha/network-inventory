package com.network.inventory.location_service.dto.request.rack_position;

public record UpdateRackPositionRequest(
    Long rackId,

    Integer positionU,

    Boolean occupied
) {}
