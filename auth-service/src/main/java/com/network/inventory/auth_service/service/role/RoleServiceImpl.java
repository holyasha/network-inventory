package com.network.inventory.auth_service.service.role;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.auth_service.dto.request.role.CreateRoleRequest;
import com.network.inventory.auth_service.dto.request.role.UpdateRoleRequest;
import com.network.inventory.auth_service.dto.response.RoleResponse;
import com.network.inventory.auth_service.entity.Role;
import com.network.inventory.auth_service.exeption.DuplicateResourceException;
import com.network.inventory.auth_service.exeption.ResourceNotFoundException;
import com.network.inventory.auth_service.repository.RoleRepository;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public RoleResponse createRole(CreateRoleRequest request) {
        if (roleRepository.findByName(request.name()).isPresent()) {
            throw new DuplicateResourceException("Роль с названием " + request.name() + " уже существует");
        }
        return mapToResponse(roleRepository.save(new Role(request.name())));
    }

    @Transactional
    @Override
    public RoleResponse updateRole(Long id, UpdateRoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Роль с id " + id + " не найдена"));

        if (request.name() != null) {
            roleRepository.findByName(request.name()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new DuplicateResourceException("Роль с названием " + request.name() + " уже существует");
                }
            });
            role.setName(request.name());
        }
        return mapToResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse getRoleById(Long id) {
        return mapToResponse(roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Роль с id " + id + " не найдена")));
    }

    @Override
    public RoleResponse getRoleByName(String name) {
        return mapToResponse(roleRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Роль с названием " + name + " не найдена")));
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Роль с id " + id + " не найдена");
        }
        roleRepository.deleteById(id);
    }
    
    private RoleResponse mapToResponse(Role r) {
        return new RoleResponse(r.getId(), r.getName());
    }
}
