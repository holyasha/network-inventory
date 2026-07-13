package com.network.inventory.network_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.network_service.entity.NetworkInterface;
import java.util.List;


public interface NetworkInterfaceRepository extends JpaRepository<NetworkInterface, Long> {

    Optional<NetworkInterface> findByMacAddress(String macAddress);

    List<NetworkInterface> findByDeviceId(Long deviceId);
    
}
