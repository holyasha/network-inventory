package com.network.inventory.location_service.service.rack;

import java.util.List;

import com.network.inventory.location_service.dto.request.rack.CreateRackRequest;
import com.network.inventory.location_service.dto.request.rack.UpdateRackRequest;
import com.network.inventory.location_service.dto.response.RackResponse;

public interface RackService {

    RackResponse createRack(CreateRackRequest request);

    RackResponse updateRack(Long id, UpdateRackRequest request);

    RackResponse getRackById(Long id);

    List<RackResponse> getRackByRoom(Long roomId);

    void deleteRack(Long id);
}
