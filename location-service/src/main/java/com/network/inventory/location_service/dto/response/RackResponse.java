package com.network.inventory.location_service.dto.response;

public record RackResponse(
    
    Long id,

    RoomInfo room,

    String code,

    Integer height,

    String manufacturer,

    String description
) {
    public record RoomInfo(
        Long id,
        StationInfo station,
        String name
    ) {
        public record StationInfo(
            Long id,
            String name,
            String line
        ) {
        }
    }
}
