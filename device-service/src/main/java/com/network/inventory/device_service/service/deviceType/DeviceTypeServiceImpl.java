package com.network.inventory.device_service.service.deviceType;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.device_service.dto.event.AuditEventDto;
import com.network.inventory.device_service.dto.request.deviceType.CreateDeviceTypeRequest;
import com.network.inventory.device_service.dto.request.deviceType.UpdateDeviceTypeRequest;
import com.network.inventory.device_service.dto.response.DeviceTypeResponse;
import com.network.inventory.device_service.entity.DeviceType;
import com.network.inventory.device_service.exeption.DuplicateResourceException;
import com.network.inventory.device_service.exeption.ResourceNotFoundException;
import com.network.inventory.device_service.repository.DeviceTypeRepository;
import com.network.inventory.device_service.service.AuditProducer;

@Service
@Transactional(readOnly = true)
public class DeviceTypeServiceImpl implements DeviceTypeService{

    private final DeviceTypeRepository deviceTypeRepository;
    private final AuditProducer auditProducer;

    
    public DeviceTypeServiceImpl(DeviceTypeRepository deviceTypeRepository, AuditProducer auditProducer) {
        this.deviceTypeRepository = deviceTypeRepository;
        this.auditProducer = auditProducer;
    }

    @Transactional
    @Override
    public DeviceTypeResponse createDeviceType(CreateDeviceTypeRequest request) {
        if(deviceTypeRepository.findByName(request.name()).isPresent()) {
            throw new DuplicateResourceException("Тип устройства с наименованием " +  request.name() + " уже существует");
        }
        DeviceType deviceType = new DeviceType(
            request.name(),
            request.description()
        );
        DeviceType saved = deviceTypeRepository.save(deviceType);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "device-service", 
            "DeviceType", 
            saved.getId(), 
            "CREATE",
            "system" //замена
        ));
        return mapToResponse(saved);
    }

    @Transactional
    @Override
    public DeviceTypeResponse updateDeviceType(Long id, UpdateDeviceTypeRequest request) {
        DeviceType deviceType = deviceTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Тип устройства с id" + id + "не найден"));
        if (deviceTypeRepository.findByName(request.name()).isPresent()) {
            throw new ResourceNotFoundException("Тип устройства с наименованием " + request.name() + " уже существует");
        }
        deviceType.setName(request.name());
        deviceType.setDescription(request.description());
        DeviceType saved = deviceTypeRepository.save(deviceType);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "device-service", 
            "DeviceType", 
            id, 
            "UPDATE",
            "system" //замена
        ));
        return mapToResponse(saved);
    }

    @Override
    public DeviceTypeResponse getDeviceTypeById(Long id) {
        return mapToResponse(deviceTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Тип устройства с id " + id + " уже существует")));
    }

    @Override
    public DeviceTypeResponse getDeviceTypeByName(String name) {
        return mapToResponse(deviceTypeRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Тип устройства с наименованием " + name + " уже существует")));
    }

    @Override
    public List<DeviceTypeResponse> getAllDeviceTypes() {
        return  deviceTypeRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Transactional
    @Override
    public void deleteDeviceType(Long id) {
        if (deviceTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Тип устройства с id " + id + " не найден");
        }
        deviceTypeRepository.deleteById(id);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "device-service", 
            "DeviceType", 
            id,
            "DELETE",
            "system" //замена
        ));
    }

    private DeviceTypeResponse mapToResponse(DeviceType d) {
        return new DeviceTypeResponse(
            d.getId(),
            d.getName(),
            d.getDescription()
        );
    }
    
}
