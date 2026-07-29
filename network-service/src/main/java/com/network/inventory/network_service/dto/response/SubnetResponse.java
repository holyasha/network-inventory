package com.network.inventory.network_service.dto.response;

public record SubnetResponse(
    Long id,
    String network,
    Integer mask,
    String gateway,
    VlanInfo vlan,
    String description
  ) {
    public record VlanInfo(
        Long id,
        Integer number,
        String name
      ) {}
  }
