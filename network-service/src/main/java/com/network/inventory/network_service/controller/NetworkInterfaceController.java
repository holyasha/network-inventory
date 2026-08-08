package com.network.inventory.network_service.controller;

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

import com.network.inventory.network_service.dto.request.networkAddress.CreateNetworkInterfaceRequest;
import com.network.inventory.network_service.dto.request.networkAddress.UpdateNetworkInterfaceRequest;
import com.network.inventory.network_service.dto.response.NetworkInterfaceResponse;
import com.network.inventory.network_service.service.networkInterface.NetworkInterfaceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/network-interfaces")
public class NetworkInterfaceController {
    private final NetworkInterfaceService networkInterfaceService;

    public NetworkInterfaceController(NetworkInterfaceService networkInterfaceService) {
        this.networkInterfaceService = networkInterfaceService;
    }

    @PostMapping
    public ResponseEntity<NetworkInterfaceResponse> createNetworkInterface(
        @Valid @RequestBody CreateNetworkInterfaceRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(networkInterfaceService.createNetworkInterface(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NetworkInterfaceResponse> updateNetworkInterface(
        @PathVariable Long id,
        @Valid @RequestBody UpdateNetworkInterfaceRequest request
    ) {
        return ResponseEntity.ok(networkInterfaceService.updateNetworkInterface(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NetworkInterfaceResponse> getNetworkInterfaceById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(networkInterfaceService.getNetworkInterfaceById(id));
    }

    @GetMapping("/mac/{macAddress}")
    public ResponseEntity<NetworkInterfaceResponse> getNetworkInterfaceByMacAddress(
        @PathVariable String macAddress
    ) {
        return ResponseEntity.ok(networkInterfaceService.getNetworkInterfaceByMacAddress(macAddress));
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<NetworkInterfaceResponse>> getNetworkInterfacesByDevice(
        @PathVariable Long deviceId
    ) {
        return ResponseEntity.ok(networkInterfaceService.getNetworkInterfacesByDevice(deviceId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNetworkInterface(
        @PathVariable Long id
    ) {
        networkInterfaceService.deleteNetworkInterface(id);
        return ResponseEntity.noContent().build();
    }
}
