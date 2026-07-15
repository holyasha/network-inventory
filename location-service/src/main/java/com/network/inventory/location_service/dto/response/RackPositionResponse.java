package com.network.inventory.location_service.dto.response;

public record RackPositionResponse(

    Long id,

    RackInfo rack,

    Integer positionU,

    Boolean occupied
) {
    public record RackInfo(
        Long id,
        RoomInfo room,
        String code
    ) {
        public record RoomInfo(
        Long id,
        Long stationId,
        String name
    ) {}
    }
}
