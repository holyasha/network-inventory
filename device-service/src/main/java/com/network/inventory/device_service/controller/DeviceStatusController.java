package com.network.inventory.device_service.controller;

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

import com.network.inventory.device_service.dto.request.deviceStatus.CreateDeviceStatusRequest;
import com.network.inventory.device_service.dto.request.deviceStatus.UpdateDeviceStatusRequest;
import com.network.inventory.device_service.dto.response.DeviceStatusResponse;
import com.network.inventory.device_service.service.deviceStatus.DeviceStatusService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/device-statuses")
public class DeviceStatusController {
    private final DeviceStatusService deviceStatusService;

    public DeviceStatusController(DeviceStatusService deviceStatusService) {
        this.deviceStatusService = deviceStatusService;
    }

    @PostMapping
    public ResponseEntity<DeviceStatusResponse> createDeviceStatus(
        @Valid @RequestBody CreateDeviceStatusRequest request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(deviceStatusService.createDeviceStatus(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceStatusResponse> updateDeviceStatus(
        @PathVariable Long id,
        @Valid @RequestBody UpdateDeviceStatusRequest request) {
            return ResponseEntity.ok(deviceStatusService.updateDeviceStatus(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceStatusResponse> getDeviceStatusById(
        @PathVariable Long id) {
            return ResponseEntity.ok(deviceStatusService.getDeviceStatusById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DeviceStatusResponse> getDeviceStatusByName(
        @PathVariable String name) {
            return ResponseEntity.ok(deviceStatusService.getDeviceStatusByName(name));
    }

    @GetMapping
    public ResponseEntity<List<DeviceStatusResponse>> getAllDeviceStatuses() {
            return ResponseEntity.ok(deviceStatusService.getAllStatuses());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceStatus(
        @PathVariable Long id) {
            deviceStatusService.deleteDeviceStatus(id);
            return ResponseEntity.noContent().build();
    }
}
