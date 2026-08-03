package com.network.inventory.device_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.network.inventory.device_service.dto.request.manufacturer.CreateManufacturerRequest;
import com.network.inventory.device_service.dto.request.manufacturer.UpdateManufacturerRequest;
import com.network.inventory.device_service.dto.response.ManufacturerResponse;
import com.network.inventory.device_service.service.manufacturer.ManufacturerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @PostMapping
    public ResponseEntity<ManufacturerResponse> createManufacturer (
        @Valid @RequestBody CreateManufacturerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manufacturerService.createManufacturer(request));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ManufacturerResponse> updateManufacturer(
        @PathVariable Long id,
        @Valid @RequestBody UpdateManufacturerRequest request
    ) {
        return ResponseEntity.ok(manufacturerService.updateManufacturer(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerResponse> getManufacturerById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(manufacturerService.getManufacturerById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ManufacturerResponse> getManufacturerByName(
        @PathVariable String name
    ) {
        return ResponseEntity.ok(manufacturerService.getManufacturerByName(name));
    }

    @GetMapping
    public ResponseEntity<List<ManufacturerResponse>> getAllManufacturers() {
        return ResponseEntity.ok(manufacturerService.getAllManufacturers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(
        @PathVariable Long id
    ) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.noContent().build();
    }
}
