package com.network.inventory.location_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.location_service.entity.Station;
import java.util.List;


public interface StationRepository extends JpaRepository<Station, Long>{
    List<Station> findByLine(String line);
}
