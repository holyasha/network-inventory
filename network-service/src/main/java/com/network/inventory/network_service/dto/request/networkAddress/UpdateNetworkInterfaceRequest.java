package com.network.inventory.network_service.dto.request.networkAddress;

public record UpdateNetworkInterfaceRequest(
    Long deviceId,
    String macAddress,
    String hostname,
    Long ipAddressId,
    Integer speed,
    String duplex,
    Boolean enabled
) {}
