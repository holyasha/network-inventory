package com.network.inventory.audit_service.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.network.inventory.audit_service.dto.AuditLogResponse;
import com.network.inventory.audit_service.dto.CreateAuditLogRequest;
import com.network.inventory.audit_service.service.AuditLogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {
    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @PostMapping
    public ResponseEntity<AuditLogResponse> createAuditLog(
        @Valid @RequestBody CreateAuditLogRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auditLogService.createAuditLog(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponse> getAuditLogById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(auditLogService.getAuditLogById(id));
    }

    @GetMapping("/service/{serviceName}")
    public ResponseEntity<Page<AuditLogResponse>> getAuditLogsByService(
        @PathVariable String serviceName,
        Pageable pageable
    ) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByService(serviceName, pageable));
    }

    @GetMapping("/entity/{entityName}/{entityId}")
    public ResponseEntity<Page<AuditLogResponse>> getAuditLogsByEntity(
        @PathVariable String entityName,
        @PathVariable Long entityId,
        Pageable pageable
    ) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByEntity(entityName, entityId, pageable));
    }

    @GetMapping("/user/{userLogin}")
    public ResponseEntity<Page<AuditLogResponse>> getAuditLogsByUser(
        @PathVariable String userLogin,
        Pageable pageable
    ) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByUser(userLogin, pageable));
    }

    @GetMapping("/time-range")
    public ResponseEntity<Page<AuditLogResponse>> getAuditLogsByTimeRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
        Pageable pageable
    ) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByTimeRange(start, end, pageable));
    }
}
