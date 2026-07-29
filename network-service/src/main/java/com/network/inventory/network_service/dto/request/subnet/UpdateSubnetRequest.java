package com.network.inventory.network_service.dto.request.subnet;

public record UpdateSubnetRequest(
    String network,
    Integer mask,
    String gateway,
    Long vlanId,
    String description
  ) {}
