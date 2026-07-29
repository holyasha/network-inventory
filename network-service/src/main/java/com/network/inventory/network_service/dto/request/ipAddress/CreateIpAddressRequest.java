package com.network.inventory.network_service.dto.request.ipAddress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateIpAddressRequest(
    @NotNull(message = "ID подсети обязателен")
    Long subnetId,

    @NotBlank(message = "IP-адрес обязателен")
    String address,

    Boolean allocated,
    Boolean reserved
) {}
