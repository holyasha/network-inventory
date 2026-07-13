package com.network.inventory.device_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.device_service.entity.Device;
import java.util.List;


public interface DeviceRepository extends JpaRepository<Device, Long>{

    Optional<Device> findByInventoryNumber(String inventoryNumber);

    Optional<Device> findBySerialNumber(String serialNumber);

    List<Device> findByStatusId(Long statusId);

    List<Device> findByDeviceModelId(Long deviceModelId);
}