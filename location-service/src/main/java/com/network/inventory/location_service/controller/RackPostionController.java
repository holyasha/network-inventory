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

import com.network.inventory.location_service.dto.request.rack_position.CreateRackPositionRequest;
import com.network.inventory.location_service.dto.request.rack_position.UpdateRackPositionRequest;
import com.network.inventory.location_service.dto.response.RackPositionResponse;
import com.network.inventory.location_service.service.rack_position.RackPositionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rack-positions")
public class RackPostionController {
    private final RackPositionService rackPositionService;

    public RackPostionController(RackPositionService rackPositionService) {
        this.rackPositionService = rackPositionService;
    }

    @PostMapping
    public ResponseEntity<RackPositionResponse> createRackPosition(
        @Valid @RequestBody CreateRackPositionRequest request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(rackPositionService.createRackPosition(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RackPositionResponse> updateRackPosition(
        @PathVariable Long id,
        @Valid @RequestBody UpdateRackPositionRequest request
    ) {
        return ResponseEntity.ok(rackPositionService.updateRackPosition(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RackPositionResponse> getRackPositionById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(rackPositionService.getRackPositionById(id));
    }

    @GetMapping("/rack/{rackId}")
    public ResponseEntity<List<RackPositionResponse>> getRackPositionsByRack(
        @PathVariable Long rackId
    ) {
        return ResponseEntity.ok(rackPositionService.getRackPositionsByRack(rackId));
    }

    @GetMapping("/rack/{rackId}/available")
    public ResponseEntity<List<RackPositionResponse>> getAvailablePositions(
        @PathVariable Long rackId
    ) {
        return ResponseEntity.ok(rackPositionService.getAvailablePositions(rackId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRackPosition(
        @PathVariable Long id
    ) {
        rackPositionService.deleteRackPosition(id);
        return ResponseEntity.noContent().build();
    }
}
