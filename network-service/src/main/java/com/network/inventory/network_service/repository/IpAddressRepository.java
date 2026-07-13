package com.network.inventory.network_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.network_service.entity.IpAddress;

public interface IpAddressRepository extends JpaRepository<IpAddress, Long> {

    Optional<IpAddress> findByAddress(String address);

    List<IpAddress> findBySubnetId(Long subnetId);

    List<IpAddress> findBySubnetIdAndAllocated(Long subnetId, Boolean allocated);
}