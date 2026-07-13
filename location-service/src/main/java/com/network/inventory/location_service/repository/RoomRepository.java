package com.network.inventory.location_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.location_service.entity.Room;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStationId(Long stationId);    
}