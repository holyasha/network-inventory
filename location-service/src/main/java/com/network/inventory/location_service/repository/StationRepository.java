package com.network.inventory.location_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.network.inventory.location_service.entity.Station;
import java.util.List;
import java.util.Optional;


public interface StationRepository extends JpaRepository<Station, Long>{
    List<Station> findByLine(String line);

    List<Station> findByName(String name);
    
    @Query("SELECT s from Station s WHERE s.name = :name AND s.line = :line")
    Optional<Station> findByLineAndName(@Param("name") String name
                             ,@Param("line") String line);
}
