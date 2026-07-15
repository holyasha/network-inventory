package com.network.inventory.location_service.dto.response;


public record RoomResponse(
    Long id,

    StationInfo station,

    String name,

    String type,

    Integer floor,

    String description

) {
    public record StationInfo(
        Long id,
        String name,
        String line
    ) {}
}
