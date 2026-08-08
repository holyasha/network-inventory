package com.network.inventory.location_service.controller;

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

import com.network.inventory.location_service.dto.request.device_location.CreateDeviceLocationRequest;
import com.network.inventory.location_service.dto.request.device_location.UpdateDeviceLocationRequest;
import com.network.inventory.location_service.dto.response.DeviceLocationResponse;
import com.network.inventory.location_service.service.device_location.DeviceLocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/device-locations")
public class DeviceLocationController {
    private final DeviceLocationService deviceLocationService;

    public DeviceLocationController(DeviceLocationService deviceLocationService) {
        this.deviceLocationService = deviceLocationService;
    }

    @PostMapping
    public ResponseEntity<DeviceLocationResponse> createDeviceLocation(
        @Valid @RequestBody CreateDeviceLocationRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceLocationService.createDeviceLocation(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceLocationResponse> updateDeviceLocation(
        @PathVariable Long id,
        @Valid @RequestBody UpdateDeviceLocationRequest request
    ) {
        return ResponseEntity.ok(deviceLocationService.updateDeviceLocation(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceLocationResponse> getDeviceLocationById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(deviceLocationService.getDeviceLocationById(id));
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<DeviceLocationResponse> getDeviceLocationByDeviceId(
        @PathVariable Long deviceId
    ) {
        return ResponseEntity.ok(deviceLocationService.getDeviceLocationByDeviceId(deviceId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceLocation(
        @PathVariable Long id
    ) {
        deviceLocationService.deleteDeviceLocation(id);
        return ResponseEntity.noContent().build();
    }
}
