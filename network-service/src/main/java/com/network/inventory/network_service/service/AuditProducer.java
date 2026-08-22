package com.network.inventory.network_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.network.inventory.network_service.dto.event.AuditEventDto;


@Service
public class AuditProducer {
    private static final String TOPIC = "audit-events";
    private final KafkaTemplate<String, AuditEventDto> kafkaTemplate;

    public AuditProducer(KafkaTemplate<String, AuditEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAuditEvent(AuditEventDto event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
