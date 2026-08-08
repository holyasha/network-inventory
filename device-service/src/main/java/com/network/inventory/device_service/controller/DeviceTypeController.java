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

import com.network.inventory.device_service.dto.request.deviceType.CreateDeviceTypeRequest;
import com.network.inventory.device_service.dto.request.deviceType.UpdateDeviceTypeRequest;
import com.network.inventory.device_service.dto.response.DeviceTypeResponse;
import com.network.inventory.device_service.service.deviceType.DeviceTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/device-types")
public class DeviceTypeController {
    private final DeviceTypeService deviceTypeService;

    public DeviceTypeController(DeviceTypeService deviceTypeService) {
        this.deviceTypeService = deviceTypeService;
    }

    @PostMapping
    public ResponseEntity<DeviceTypeResponse> createDeviceType(
        @Valid @RequestBody CreateDeviceTypeRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceTypeService.createDeviceType(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceTypeResponse> updateDeviceType(
        @PathVariable Long id,
        @Valid @RequestBody UpdateDeviceTypeRequest request
    ) {
        return ResponseEntity.ok(deviceTypeService.updateDeviceType(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceTypeResponse> getDeviceTypeById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(deviceTypeService.getDeviceTypeById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DeviceTypeResponse> getDeviceTypeByName(
        @PathVariable String name
    ) {
        return ResponseEntity.ok(deviceTypeService.getDeviceTypeByName(name));
    }

    @GetMapping
    public ResponseEntity<List<DeviceTypeResponse>> getAllDeviceTypes() {
        return ResponseEntity.ok(deviceTypeService.getAllDeviceTypes());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceType(
        @PathVariable Long id
    ) {
        deviceTypeService.deleteDeviceType(id);
        return ResponseEntity.noContent().build();
    }
}
