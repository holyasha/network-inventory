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

import com.network.inventory.device_service.dto.request.deviceModel.CreateDeviceModelRequest;
import com.network.inventory.device_service.dto.request.deviceModel.UpdateDeviceModelRequest;
import com.network.inventory.device_service.dto.response.DeviceModelResponse;
import com.network.inventory.device_service.service.deviceModel.DeviceModelService;

import jakarta.validation.Valid;

public class DeviceModelController {
    private final DeviceModelService deviceModelService;

    public DeviceModelController(DeviceModelService deviceModelService) {
        this.deviceModelService = deviceModelService;
    }

    @PostMapping
    public ResponseEntity<DeviceModelResponse> createDeviceModel(
        @Valid @RequestBody CreateDeviceModelRequest request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(deviceModelService.createDeviceModel(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceModelResponse> updateDeviceModel(
        @PathVariable Long id,
        @Valid @RequestBody UpdateDeviceModelRequest request) {
            return ResponseEntity.ok(deviceModelService.updateDeviceModel(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceModelResponse> getDeviceModelById(
        @PathVariable Long id) {
            return ResponseEntity.ok(deviceModelService.getDeviceModelById(id));
    }

    @GetMapping("/manufacturer/{manufacturerId}")
    public ResponseEntity<List<DeviceModelResponse>> getDeviceModelsByManufacturer(
        @PathVariable Long manufacturerId) {
            return ResponseEntity.ok(deviceModelService.getDeviceModelByManufacturerId(manufacturerId));
    }

    @GetMapping("/type/{deviceTypeId}")
    public ResponseEntity<List<DeviceModelResponse>> getDeviceModelsByType(
        @PathVariable Long deviceTypeId) {
            return ResponseEntity.ok(deviceModelService.getDeviceModelsByDeviceTypeId(deviceTypeId));
    }

    @GetMapping
    public ResponseEntity<List<DeviceModelResponse>> getAllDeviceModels() {
        return ResponseEntity.ok(deviceModelService.getAllDeviceModels());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceModel(
        @PathVariable Long id) {
            deviceModelService.deleteDeviceModel(id);
            return ResponseEntity.noContent().build();
    }
}
