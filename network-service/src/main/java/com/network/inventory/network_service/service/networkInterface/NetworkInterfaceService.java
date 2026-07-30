package com.network.inventory.network_service.service.networkInterface;

import java.util.List;

import com.network.inventory.network_service.dto.request.networkAddress.CreateNetworkInterfaceRequest;
import com.network.inventory.network_service.dto.request.networkAddress.UpdateNetworkInterfaceRequest;
import com.network.inventory.network_service.dto.response.NetworkInterfaceResponse;

public interface NetworkInterfaceService {
    NetworkInterfaceResponse createNetworkInterface(CreateNetworkInterfaceRequest request);
    NetworkInterfaceResponse updateNetworkInterface(Long id, UpdateNetworkInterfaceRequest request);
    NetworkInterfaceResponse getNetworkInterfaceById(Long id);
    NetworkInterfaceResponse getNetworkInterfaceByMacAddress(String macAddress);
    List<NetworkInterfaceResponse> getNetworkInterfacesByDevice(Long deviceId);
    void deleteNetworkInterface(Long id);
}
