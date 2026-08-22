package com.network.inventory.audit_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.network.inventory.audit_service.dto.AuditEventDto;
import com.network.inventory.audit_service.entity.AuditLog;
import com.network.inventory.audit_service.repository.AuditlogRepository;

@Service
public class AuditConsumer {
    
    private final AuditlogRepository auditlogRepository;

    public AuditConsumer(AuditlogRepository auditlogRepository) {
        this.auditlogRepository = auditlogRepository;
    }

    @KafkaListener(topics = "audit-events", groupId = "audit-service-group")
    public void consumeAuditEvent(AuditEventDto event) {
        AuditLog auditLog = new AuditLog();
        auditLog.setServiceName(event.serviceName());
        auditLog.setEntityName(event.entityName());
        auditLog.setEntityId(event.entityId());
        auditLog.setAction(event.action());
        auditLog.setUserLogin(event.userLogin());
        auditLog.setOldValue(event.oldValue());
        auditLog.setNewValue(event.newValue());

        auditlogRepository.save(auditLog);
    }
}
