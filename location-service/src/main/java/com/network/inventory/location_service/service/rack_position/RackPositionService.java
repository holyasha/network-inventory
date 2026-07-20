package com.network.inventory.location_service.service.rack_position;

import java.util.List;

import com.network.inventory.location_service.dto.request.rack_position.CreateRackPositionRequest;
import com.network.inventory.location_service.dto.request.rack_position.UpdateRackPositionRequest;
import com.network.inventory.location_service.dto.response.RackPositionResponse;

public interface RackPositionService {
    
    RackPositionResponse createRackPosition(CreateRackPositionRequest request);

    RackPositionResponse updateRackPosition(Long id, UpdateRackPositionRequest request);

    RackPositionResponse getRackPositionById(Long id);

    List<RackPositionResponse> getRackPositionsByRack(Long rackId);

    List<RackPositionResponse> getAvailablePositions(Long rackId);

    void deleteRackPosition(Long id);
}
