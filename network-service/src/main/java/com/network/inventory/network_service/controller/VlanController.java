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

import com.network.inventory.network_service.dto.request.vlan.CreateVlanRequest;
import com.network.inventory.network_service.dto.request.vlan.UpdateVlanRequest;
import com.network.inventory.network_service.dto.response.VlanResponse;
import com.network.inventory.network_service.service.vlan.VlanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vlans")
public class VlanController {
    private final VlanService vlanService;

    public VlanController(VlanService vlanService) {
        this.vlanService = vlanService;
    }

    @PostMapping
    public ResponseEntity<VlanResponse> createVlan(
        @Valid @RequestBody CreateVlanRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vlanService.createVlan(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VlanResponse> updateVlan(
        @PathVariable Long id,
        @Valid @RequestBody UpdateVlanRequest request
    ) {
        return ResponseEntity.ok(vlanService.updateVlan(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VlanResponse> getVlanById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(vlanService.getVlanById(id));
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<VlanResponse> getVlanByNumber(
        @PathVariable Integer number
    ) {
        return ResponseEntity.ok(vlanService.getVlanByNumber(number));
    }

    @GetMapping
    public ResponseEntity<List<VlanResponse>> getAllVlans() {
        return ResponseEntity.ok(vlanService.getAllVlans());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVlan(
        @PathVariable Long id
    ) {
        vlanService.deleteVlan(id);
        return ResponseEntity.noContent().build();
    }
}
