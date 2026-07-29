package com.network.inventory.network_service.dto.request.vlan;

public record UpdateVlanRequest(
    Integer number,
    String name,
    String description
  ) {}
