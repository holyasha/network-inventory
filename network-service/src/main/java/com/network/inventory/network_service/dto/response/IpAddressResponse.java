package com.network.inventory.network_service.dto.response;

public record IpAddressResponse(
    Long id,
    SubnetInfo subnet,
    String address,
    Boolean allocated,
    Boolean reserved
  ) {
    public record SubnetInfo(
        Long id,
        String network,
        Integer mask
    ) {}
}
