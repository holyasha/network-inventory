package com.network.inventory.network_service.dto.request.vlan;

 import jakarta.validation.constraints.NotNull;

public record CreateVlanRequest(
    @NotNull(message = "Номер VLAN обязателен")
    Integer number,
    
    String name,
    String description
  ) {}