package com.network.inventory.network_service.controller;

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

import com.network.inventory.network_service.dto.request.ipAddress.CreateIpAddressRequest;
import com.network.inventory.network_service.dto.request.ipAddress.UpdateIpAddressRequest;
import com.network.inventory.network_service.dto.response.IpAddressResponse;
import com.network.inventory.network_service.service.ipAddress.IpAddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ip-addresses")
public class IpAddressController {
    private final IpAddressService ipAddressService;

    public IpAddressController(IpAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    @PostMapping
    public ResponseEntity<IpAddressResponse> createIpAddress(
        @Valid @RequestBody CreateIpAddressRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ipAddressService.createIpAddress(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IpAddressResponse> updateIpAddress(
        @PathVariable Long id,
        @Valid @RequestBody UpdateIpAddressRequest request
    ) {
        return ResponseEntity.ok(ipAddressService.updateIpAddress(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IpAddressResponse> getIpAddressById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(ipAddressService.getIpAddressById(id));
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<IpAddressResponse> getIpAddressByAddress(
        @PathVariable String address
    ) {
        return ResponseEntity.ok(ipAddressService.getIpAddressByAddress(address));
    }

    @GetMapping("/subnet/{subnetId}")
    public ResponseEntity<Page<IpAddressResponse>> getIpAddressesBySubnet(
        @PathVariable Long subnetId,
        Pageable pageable
    ) {
        return ResponseEntity.ok(ipAddressService.getIpAddressesBySubnet(subnetId, pageable));
    }

    @GetMapping("/subnet/{subnetId}/available")
    public ResponseEntity<Page<IpAddressResponse>> getAvailableIpAddresses(
        @PathVariable Long subnetId,
        Pageable pageable
    ) {
        return ResponseEntity.ok(ipAddressService.getAvailableIpAddresses(subnetId, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIpAddress(
        @PathVariable Long id
    ) {
        ipAddressService.deleteIpAddress(id);
        return ResponseEntity.noContent().build();
    }
}
