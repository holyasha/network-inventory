package com.network.inventory.device_service.exeption;

import java.time.LocalDateTime;

public record ErrorResponse(
              int status,
              String message,
              LocalDateTime timestamp
      ) {}
