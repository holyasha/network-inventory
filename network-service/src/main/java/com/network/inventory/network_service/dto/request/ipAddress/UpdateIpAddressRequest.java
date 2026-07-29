package com.network.inventory.network_service.dto.request.ipAddress;

public record UpdateIpAddressRequest(
    Long subnetId,
    String address,
    Boolean allocated,
    Boolean reserved
) {}
