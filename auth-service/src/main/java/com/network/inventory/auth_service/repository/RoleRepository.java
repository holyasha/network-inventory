package com.network.inventory.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.auth_service.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
