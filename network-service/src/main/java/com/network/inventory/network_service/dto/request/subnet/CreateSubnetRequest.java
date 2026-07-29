package com.network.inventory.network_service.dto.request.subnet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSubnetRequest(
    @NotBlank(message = "Сеть обязательна")
    String network,

    @NotNull(message = "Маска обязательна")
    Integer mask,

    String gateway,
    Long vlanId,
    String description
  ) {}
