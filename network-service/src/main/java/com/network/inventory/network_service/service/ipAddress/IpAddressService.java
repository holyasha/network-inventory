package com.network.inventory.network_service.service.ipAddress;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.network.inventory.network_service.dto.request.ipAddress.CreateIpAddressRequest;
import com.network.inventory.network_service.dto.request.ipAddress.UpdateIpAddressRequest;
import com.network.inventory.network_service.dto.response.IpAddressResponse;

public interface IpAddressService {
    IpAddressResponse createIpAddress(CreateIpAddressRequest request);
    IpAddressResponse updateIpAddress(Long id, UpdateIpAddressRequest request);
    IpAddressResponse getIpAddressById(Long id);
    IpAddressResponse getIpAddressByAddress(String address);
    Page<IpAddressResponse> getIpAddressesBySubnet(Long subnetId, Pageable pageable);
    Page<IpAddressResponse> getAvailableIpAddresses(Long subnetId, Pageable pageable);
    void deleteIpAddress(Long id);
}
