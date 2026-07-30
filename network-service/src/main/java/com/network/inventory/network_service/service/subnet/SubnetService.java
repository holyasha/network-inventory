package com.network.inventory.network_service.service.subnet;

import java.util.List;

import com.network.inventory.network_service.dto.request.subnet.CreateSubnetRequest;
import com.network.inventory.network_service.dto.request.subnet.UpdateSubnetRequest;
import com.network.inventory.network_service.dto.response.SubnetResponse;

public interface SubnetService {
    SubnetResponse createSubnet(CreateSubnetRequest request);
    SubnetResponse updateSubnet(Long id, UpdateSubnetRequest request);
    SubnetResponse getSubnetById(Long id);
    List<SubnetResponse> getSubnetsByVlan(Long vlanId);
    List<SubnetResponse> getAllSubnets();
    void deleteSubnet(Long id);
}
