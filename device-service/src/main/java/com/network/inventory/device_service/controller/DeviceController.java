package com.network.inventory.device_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.network.inventory.device_service.dto.request.device.CreateDeviceRequest;
import com.network.inventory.device_service.dto.request.device.UpdateDeviceRequest;
import com.network.inventory.device_service.dto.response.DeviceListItemResponse;
import com.network.inventory.device_service.dto.response.DeviceResponse;
import com.network.inventory.device_service.service.device.DeviceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(
        @Valid @RequestBody CreateDeviceRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.createDevice(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> updateDevice(
        @PathVariable Long id,
        @Valid @RequestBody UpdateDeviceRequest request
    ) {
        return ResponseEntity.ok(deviceService.updateDevice(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    @GetMapping("/inventory/{inventoryNumber}")
    public ResponseEntity<DeviceResponse> getDeviceByInventoryNumber(
        @PathVariable String inventoryNumber
    ) {
        return ResponseEntity.ok(deviceService.getDeviceByInventoryNumber(inventoryNumber));
    }

    @GetMapping
    public ResponseEntity<Page<DeviceListItemResponse>> getAllDevices(Pageable pageable) {
        return ResponseEntity.ok(deviceService.getAllDevices(pageable));
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<Page<DeviceListItemResponse>> getDevicesByStatus(
        @PathVariable Long statusId,
        Pageable pageable
    ) {
        return ResponseEntity.ok(deviceService.getDevicesByStatus(statusId, pageable));
    }

    @GetMapping("/model/{modelId}")
    public ResponseEntity<Page<DeviceListItemResponse>> getDevicesByModel(
        @PathVariable Long modelId,
        Pageable pageable
    ) {
        return ResponseEntity.ok(deviceService.getDevicesByModel(modelId, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(
        @PathVariable Long id
    ) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
