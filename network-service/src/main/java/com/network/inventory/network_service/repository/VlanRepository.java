package com.network.inventory.network_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.network_service.entity.Vlan;


public interface VlanRepository extends JpaRepository<Vlan, Long> {

    Optional<Vlan> findByNumber(Integer number);
    
}
