package com.network.inventory.audit_service.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.audit_service.dto.AuditLogResponse;
import com.network.inventory.audit_service.dto.CreateAuditLogRequest;
import com.network.inventory.audit_service.entity.AuditLog;
import com.network.inventory.audit_service.exeption.ResourceNotFoundException;
import com.network.inventory.audit_service.repository.AuditlogRepository;

@Service
@Transactional(readOnly = true)
public class AuditLogServiceImpl implements AuditLogService{

    private final AuditlogRepository auditlogRepository;

    
    public AuditLogServiceImpl(AuditlogRepository auditlogRepository) {
        this.auditlogRepository = auditlogRepository;
    }

    @Transactional
    @Override
    public AuditLogResponse createAuditLog(CreateAuditLogRequest request) {
        AuditLog auditLog = new AuditLog(
            request.serviceName(),
            request.entityName(),
            request.entityId(),
            request.action()
        );
        auditLog.setUserLogin(request.userLogin());
        auditLog.setOldValue(request.oldValue());
        auditLog.setNewValue(request.newValue());

        return mapToResponse(auditlogRepository.save(auditLog));
    }

    @Override
    public AuditLogResponse getAuditLogById(Long id) {
        return mapToResponse(auditlogRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Запись аудита не найдена с id: " + id)));
    }

    @Override
    public Page<AuditLogResponse> getAuditLogsByService(String serviceName, Pageable pageable) {
        return auditlogRepository.findByServiceName(serviceName, pageable).map(this::mapToResponse);
    }

    @Override
    public Page<AuditLogResponse> getAuditLogsByEntity(String entityName, Long entityId, Pageable pageable) {
        return auditlogRepository.findByEntityIdAndEntityName(entityId, entityName, pageable).map(this::mapToResponse);
    }

    @Override
    public Page<AuditLogResponse> getAuditLogsByUser(String userLogin, Pageable pageable) {
        return auditlogRepository.findByUserLogin(userLogin, pageable).map(this::mapToResponse);
    }

    @Override
    public Page<AuditLogResponse> getAuditLogsByTimeRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return auditlogRepository.findByCreatedAtBetween(start, end, pageable).map(this::mapToResponse);
    }

    @Override
    public Page<AuditLogResponse> getAllAuditLogs(Pageable pageable) {
        return auditlogRepository.findAll(pageable).map(this::mapToResponse);
    }
    
    private final AuditLogResponse mapToResponse(AuditLog a) {
        return new AuditLogResponse(
            a.getId(),
            a.getServiceName(),
            a.getEntityName(),
            a.getEntityId(),
            a.getAction(),
            a.getUserLogin(),
            a.getOldValue(),
            a.getNewValue(),
            a.getCreatedAt()
        );
    }
}
