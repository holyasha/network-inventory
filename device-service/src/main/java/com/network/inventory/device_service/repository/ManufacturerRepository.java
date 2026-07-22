package com.network.inventory.device_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.device_service.entity.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>{
    
    Optional<Manufacturer> findByName(String name);
}
