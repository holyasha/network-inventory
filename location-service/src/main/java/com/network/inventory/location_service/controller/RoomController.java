package com.network.inventory.location_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.network.inventory.location_service.dto.request.room.CreateRoomRequest;
import com.network.inventory.location_service.dto.request.room.UpdateRoomRequest;
import com.network.inventory.location_service.dto.response.RoomResponse;
import com.network.inventory.location_service.service.room.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
        @Valid @RequestBody CreateRoomRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(
        @PathVariable Long id,
        @Valid @RequestBody UpdateRoomRequest request
    ) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/station/{stationId}")
    public ResponseEntity<List<RoomResponse>> getRoomsByStation(
        @PathVariable Long stationId
    ) {
        return ResponseEntity.ok(roomService.getRoomsByStation(stationId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(
        @PathVariable Long id
    ) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
