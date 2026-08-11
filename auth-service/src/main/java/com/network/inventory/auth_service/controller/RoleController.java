package com.network.inventory.auth_service.controller;

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

import com.network.inventory.auth_service.dto.request.role.CreateRoleRequest;
import com.network.inventory.auth_service.dto.request.role.UpdateRoleRequest;
import com.network.inventory.auth_service.dto.response.RoleResponse;
import com.network.inventory.auth_service.service.role.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(
        @Valid @RequestBody CreateRoleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
        @PathVariable Long id,
        @Valid @RequestBody UpdateRoleRequest request
    ) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RoleResponse> getRoleByName(
        @PathVariable String name
    ) {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(
        @PathVariable Long id
    ) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
