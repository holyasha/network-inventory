package com.network.inventory.audit_service.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.network.inventory.audit_service.dto.AuditLogResponse;
import com.network.inventory.audit_service.dto.CreateAuditLogRequest;

public interface AuditLogService {
    AuditLogResponse createAuditLog(CreateAuditLogRequest request);
    AuditLogResponse getAuditLogById(Long id);
    Page<AuditLogResponse> getAuditLogsByService(String serviceName, Pageable pageable);
    Page<AuditLogResponse> getAuditLogsByEntity(String entityName, Long entityId, Pageable pageable);
    Page<AuditLogResponse> getAuditLogsByUser(String userLogin, Pageable pageable);
    Page<AuditLogResponse> getAuditLogsByTimeRange(LocalDateTime start, LocalDateTime end, Pageable pageable);
    Page<AuditLogResponse> getAllAuditLogs(Pageable pageable);
}
