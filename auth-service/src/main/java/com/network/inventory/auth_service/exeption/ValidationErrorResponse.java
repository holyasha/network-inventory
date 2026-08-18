package com.network.inventory.auth_service.exeption;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(
              int status,
              String message,
              Map<String, String> errors,
              LocalDateTime timestamp
      ) {}
