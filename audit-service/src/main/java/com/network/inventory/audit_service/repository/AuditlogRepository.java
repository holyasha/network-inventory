package com.network.inventory.audit_service.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.audit_service.entity.AuditLog;

public interface AuditlogRepository extends JpaRepository<AuditLog, Long> {
    Page<AuditLog> findByServiceName(String serviceName, Pageable pageable);

    Page<AuditLog> findByEntityIdAndEntityName(Long entityId, String entityName, Pageable pageable);

    Page<AuditLog> findByUserLogin(String userLogin, Pageable pageable);

    Page<AuditLog> findByCreatedAtBetween(LocalDateTime createdAt, LocalDateTime endDateTime, Pageable pageable);
}
