package com.network.inventory.location_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.location_service.entity.RackPosition;
import java.util.List;


public interface RackPositionRepository extends JpaRepository<RackPosition, Long> {

    List<RackPosition> findByRackId(Long rackId);

    List<RackPosition> findByRackIdAndOccupied(Long rackId, Boolean occupied);
}