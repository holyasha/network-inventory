package com.network.inventory.device_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.device_service.entity.DeviceModel;

public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long>{

    List<DeviceModel> findByManufacturerId(Long manufacturerId);

    List<DeviceModel> findByDeviceTypeId(Long deviceTypeId);

    Optional<DeviceModel> findByModelAndManufacturer(String model, Long manufacturerId);
}
