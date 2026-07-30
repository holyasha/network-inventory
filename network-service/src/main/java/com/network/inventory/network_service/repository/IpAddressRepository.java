package com.network.inventory.network_service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.network_service.entity.IpAddress;

public interface IpAddressRepository extends JpaRepository<IpAddress, Long> {

    Optional<IpAddress> findByAddress(String address);

    Page<IpAddress> findBySubnetId(Long subnetId, Pageable pageable);

    Page<IpAddress> findBySubnetIdAndAllocated(Long subnetId, Boolean allocated, Pageable pageable);
}