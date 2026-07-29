package com.network.inventory.network_service.dto.response;

public record NetworkInterfaceResponse(
    Long id,
    Long deviceId,
    String hostname,
    String macAddress,
    IpAddressInfo ipAddress,
    Integer speed,
    String duplex,
    Boolean enabled
  ) {
    public record IpAddressInfo(
        Long id,
        String address,
        String subnetNetwork
    ) {}
}
