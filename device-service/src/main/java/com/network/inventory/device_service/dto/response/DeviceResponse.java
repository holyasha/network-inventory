package com.network.inventory.device_service.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DeviceResponse(

    Long id,

    String inventoryNumber,

    String serialNumber,
    
    DeviceModelInfo deviceModel,
    
    DeviceStatusInfo status,
    
    LocalDate purchaseDate,
    
    LocalDate installationDate,
    
    LocalDate warrantyUntil,
    
    String comment,
    
    LocalDateTime createdAt,
    
    LocalDateTime updatedAt
) {
    public record DeviceModelInfo(
          Long id,
          String model,
          String manufacturerName,
          String deviceTypeName
      ) {}

      public record DeviceStatusInfo(
          Long id,
          String name
      ) {}
}
