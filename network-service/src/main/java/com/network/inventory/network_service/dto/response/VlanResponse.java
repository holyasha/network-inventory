package com.network.inventory.network_service.dto.response;

public record VlanResponse(
    Long id,
    Integer number,
    String name,
    String description
  ) {}
