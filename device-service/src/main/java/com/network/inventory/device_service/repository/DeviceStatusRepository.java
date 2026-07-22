package com.network.inventory.device_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.device_service.entity.DeviceStatus;
import java.util.List;
import java.util.Optional;


public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long>{
    Optional<DeviceStatus> findByName(String name);

    List<DeviceStatus> findByColor(String color);

}
