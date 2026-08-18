package com.network.inventory.auth_service.exeption;

import java.time.LocalDateTime;

public record ErrorResponse(
              int status,
              String message,
              LocalDateTime timestamp
      ) {}
