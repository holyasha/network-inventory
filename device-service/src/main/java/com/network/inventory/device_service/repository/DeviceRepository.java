package com.network.inventory.device_service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.device_service.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long>{

    Optional<Device> findByInventoryNumber(String inventoryNumber);

    Optional<Device> findBySerialNumber(String serialNumber);

    Page<Device> findByStatusId(Long statusId, Pageable pageable);

    Page<Device> findByDeviceModelId(Long deviceModelId, Pageable pageable);
}