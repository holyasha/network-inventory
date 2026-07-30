package com.network.inventory.network_service.service.vlan;

import java.util.List;

import com.network.inventory.network_service.dto.request.vlan.CreateVlanRequest;
import com.network.inventory.network_service.dto.request.vlan.UpdateVlanRequest;
import com.network.inventory.network_service.dto.response.VlanResponse;

public interface VlanService {
    
    VlanResponse createVlan(CreateVlanRequest request);

    VlanResponse updateVlan(Long id, UpdateVlanRequest request);

    VlanResponse getVlanById(Long id);

    VlanResponse getVlanByNumber(Integer number);

    List<VlanResponse> getAllVlans();

    void deleteVlan(Long id);
}
