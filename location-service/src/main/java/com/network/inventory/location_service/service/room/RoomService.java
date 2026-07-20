package com.network.inventory.location_service.service.room;

import java.util.List;

import com.network.inventory.location_service.dto.request.room.CreateRoomRequest;
import com.network.inventory.location_service.dto.request.room.UpdateRoomRequest;
import com.network.inventory.location_service.dto.response.RoomResponse;

public interface RoomService {

    RoomResponse createRoom(CreateRoomRequest request);

    RoomResponse updateRoom(Long id, UpdateRoomRequest request);

    RoomResponse getRoomById(Long id);

    List<RoomResponse> getRoomsByStation(Long stationId);

    void deleteRoom(Long id);
}
