package com.network.inventory.auth_service.service.role;

import java.util.List;

import com.network.inventory.auth_service.dto.request.role.CreateRoleRequest;
import com.network.inventory.auth_service.dto.request.role.UpdateRoleRequest;
import com.network.inventory.auth_service.dto.response.RoleResponse;

public interface RoleService {
    RoleResponse createRole(CreateRoleRequest request);
    RoleResponse updateRole(Long id, UpdateRoleRequest request);
    RoleResponse getRoleById(Long id);
    RoleResponse getRoleByName(String name);
    List<RoleResponse> getAllRoles();
    void deleteRole(Long id);
}
