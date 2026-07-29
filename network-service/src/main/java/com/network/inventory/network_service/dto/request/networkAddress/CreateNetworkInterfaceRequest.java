package com.network.inventory.network_service.dto.request.networkAddress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateNetworkInterfaceRequest(
    @NotNull(message = "ID устройства обязателен")
    Long deviceId,

    @NotBlank(message = "MAC-адрес обязателен")
    String macAddress,

    String hostname,
    Long ipAddressId,
    Integer speed,
    String duplex,
    Boolean enabled
) {}
