package com.network.inventory.network_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.network_service.entity.Vlan;
import java.util.List;


public interface VlanRepository extends JpaRepository<Vlan, Long> {

    List<Vlan> findByNumber(Integer number);
    
}
