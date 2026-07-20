package com.network.inventory.location_service.service.station;

import java.util.List;

import com.network.inventory.location_service.dto.request.station.CreateStationRequest;
import com.network.inventory.location_service.dto.request.station.UpdateStationRequest;
import com.network.inventory.location_service.dto.response.StationResponse;

public interface StationService {

    StationResponse createStation(CreateStationRequest request);

    StationResponse updateStation(Long id, UpdateStationRequest request);

    StationResponse getStationById(Long id);

    List<StationResponse> getStationByName(String name);

    List<StationResponse> getAllStations();

    List<StationResponse> getStatiionsByLine(String line);

    void deleteStation(Long id);
    
}
