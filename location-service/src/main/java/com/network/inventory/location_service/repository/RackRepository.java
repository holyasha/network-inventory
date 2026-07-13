package com.network.inventory.location_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.location_service.entity.Rack;
import java.util.List;


public interface RackRepository extends JpaRepository<Rack, Long> {
    List<Rack> findByRoomId(Long roomId);    
}
