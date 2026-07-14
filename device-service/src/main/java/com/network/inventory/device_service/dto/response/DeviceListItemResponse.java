package com.network.inventory.device_service.dto.response;

import java.time.LocalDate;

public record DeviceListItemResponse(

    Long id,

    String inventoryNumber,

    String serialNumber,

    String modelName,

    String statusName,

    LocalDate installationDate
) {}
