package com.network.inventory.location_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.location_service.entity.DeviceLocation;
import java.util.Optional;


public interface DeviceLocationRepository extends JpaRepository<DeviceLocation, Long> {
    Optional<DeviceLocation> findByDeviceId(Long deviceId);
    
}
