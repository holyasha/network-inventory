package com.network.inventory.device_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.device_service.entity.DeviceStatus;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long>{}
