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

import com.network.inventory.location_service.dto.request.station.CreateStationRequest;
import com.network.inventory.location_service.dto.request.station.UpdateStationRequest;
import com.network.inventory.location_service.dto.response.StationResponse;
import com.network.inventory.location_service.service.station.StationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stations")
public class StationController {
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<StationResponse> createStation(
        @Valid @RequestBody CreateStationRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stationService.createStation(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StationResponse> updateStation(
        @PathVariable Long id,
        @Valid @RequestBody UpdateStationRequest request
    ) {
        return ResponseEntity.ok(stationService.updateStation(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationResponse> getStationById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(stationService.getStationById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<StationResponse>> getStationByName(
        @PathVariable String name
    ) {
        return ResponseEntity.ok(stationService.getStationByName(name));
    }

    @GetMapping("/line/{line}")
    public ResponseEntity<List<StationResponse>> getStationsByLine(
        @PathVariable String line
    ) {
        return ResponseEntity.ok(stationService.getStatiionsByLine(line));
    }

    @GetMapping
    public ResponseEntity<List<StationResponse>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(
        @PathVariable Long id
    ) {
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }
}
