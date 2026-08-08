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

import com.network.inventory.network_service.dto.request.subnet.CreateSubnetRequest;
import com.network.inventory.network_service.dto.request.subnet.UpdateSubnetRequest;
import com.network.inventory.network_service.dto.response.SubnetResponse;
import com.network.inventory.network_service.service.subnet.SubnetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/subnets")
public class SubnetController {
    private final SubnetService subnetService;

    public SubnetController(SubnetService subnetService) {
        this.subnetService = subnetService;
    }
    
    @PostMapping
    public ResponseEntity<SubnetResponse> createSubnet(
        @Valid @RequestBody CreateSubnetRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subnetService.createSubnet(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubnetResponse> updateSubnet(
        @PathVariable Long id,
        @Valid @RequestBody UpdateSubnetRequest request
    ) {
        return ResponseEntity.ok(subnetService.updateSubnet(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubnetResponse> getSubnetById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(subnetService.getSubnetById(id));
    }

    @GetMapping("/vlan/{vlanId}")
    public ResponseEntity<List<SubnetResponse>> getSubnetsByVlan(
        @PathVariable Long vlanId
    ) {
        return ResponseEntity.ok(subnetService.getSubnetsByVlan(vlanId));
    }

    @GetMapping
    public ResponseEntity<List<SubnetResponse>> getAllSubnets() {
        return ResponseEntity.ok(subnetService.getAllSubnets());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubnet(
        @PathVariable Long id
    ) {
        subnetService.deleteSubnet(id);
        return ResponseEntity.noContent().build();
    }
}
