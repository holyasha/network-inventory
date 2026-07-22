package com.network.inventory.device_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.device_service.entity.DeviceType;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long>{
    Optional<DeviceType> findByName(String name);
}
