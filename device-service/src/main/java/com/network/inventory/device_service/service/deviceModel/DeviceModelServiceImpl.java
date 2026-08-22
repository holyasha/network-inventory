package com.network.inventory.device_service.service.deviceModel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.device_service.dto.event.AuditEventDto;
import com.network.inventory.device_service.dto.request.deviceModel.CreateDeviceModelRequest;
import com.network.inventory.device_service.dto.request.deviceModel.UpdateDeviceModelRequest;
import com.network.inventory.device_service.dto.response.DeviceModelResponse;
import com.network.inventory.device_service.entity.DeviceModel;
import com.network.inventory.device_service.entity.DeviceType;
import com.network.inventory.device_service.entity.Manufacturer;
import com.network.inventory.device_service.exeption.DuplicateResourceException;
import com.network.inventory.device_service.exeption.ResourceNotFoundException;
import com.network.inventory.device_service.repository.DeviceModelRepository;
import com.network.inventory.device_service.repository.DeviceTypeRepository;
import com.network.inventory.device_service.repository.ManufacturerRepository;
import com.network.inventory.device_service.service.AuditProducer;

@Service
@Transactional(readOnly = true)
public class DeviceModelServiceImpl implements DeviceModelService {

    private final DeviceModelRepository deviceModelRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final DeviceTypeRepository deviceTypeRepository;
    private final AuditProducer auditProducer;

    
    public DeviceModelServiceImpl(DeviceModelRepository deviceModelRepository,
            ManufacturerRepository manufacturerRepository, DeviceTypeRepository deviceTypeRepository,
            AuditProducer auditProducer) {
        this.deviceModelRepository = deviceModelRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.deviceTypeRepository = deviceTypeRepository;
        this.auditProducer = auditProducer;
    }

    @Transactional
    @Override
    public DeviceModelResponse createDeviceModel(CreateDeviceModelRequest request) {
        Manufacturer manufacturer = manufacturerRepository.findById(request.manufacturerId())
            .orElseThrow(() -> new ResourceNotFoundException("Производитель с id " + request.manufacturerId() + " не найден"));
        if (deviceModelRepository.findByModelAndManufacturer(request.model(), manufacturer.getId()).isPresent()) {
            throw new DuplicateResourceException("Мjдель устройства " + request.model() + " уже существует");
        }

        DeviceType deviceType = deviceTypeRepository.findById(request.deviceTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Тип устройства с id " + request.deviceTypeId() + " не найден"));
        
        DeviceModel deviceModel = new DeviceModel(manufacturer, deviceType, request.model());
        deviceModel.setDescription(request.description());
        deviceModel.setPortsCount(request.portsCount());
        deviceModel.setManagementType(request.managementType());
        DeviceModel saved = deviceModelRepository.save(deviceModel);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "device-service",
            "DeviceModel",
            saved.getId(),
            "CREATE",
            "system"//замена
        ));
        return mapToResponse(saved);
    }

    @Transactional
    @Override
    public DeviceModelResponse updateDeviceModel(Long id, UpdateDeviceModelRequest request) {
        DeviceModel deviceModel = deviceModelRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Модель устройства с id " + id + " не найдена"));
        
        if (request.manufacturerId()!= null) {
            Manufacturer manufacturer = manufacturerRepository.findById(request.manufacturerId())
                .orElseThrow(() -> new ResourceNotFoundException("Производитель с id " + request.manufacturerId() + " не найден"));
            deviceModelRepository.findByModelAndManufacturer(request.model(), request.manufacturerId())
            .ifPresent(existing -> {
                if(!existing.getId().equals(id)) {
                    throw new DuplicateResourceException("Данная модель " + request.model() + " устройства от производителя с id " + request.manufacturerId() + " уже существует!");
                }
            });
            deviceModel.setManufacturer(manufacturer);
        }

        if(request.deviceTypeId()!=null) {
            DeviceType deviceType = deviceTypeRepository.findById(request.deviceTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Тип устройства с id " + request.deviceTypeId() + " не найден"));
            deviceModel.setDeviceType(deviceType);
        }

        if (request.model()!=null) {
            deviceModel.setModel(request.model());
        }
        deviceModel.setDescription(request.description());
        deviceModel.setPortsCount(request.portsCount());
        deviceModel.setManagementType(request.managementType());
        DeviceModel updated = deviceModelRepository.save(deviceModel);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "device-service", 
            "DeviceModel", 
            id, 
            "UPDATE",
            "system" //замена
        ));
        return mapToResponse(updated);
    }

    @Override
    public DeviceModelResponse getDeviceModelById(Long id) {
        return mapToResponse(deviceModelRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Модель устройства с id " + id + " не найдена")));
    }

    @Override
    public List<DeviceModelResponse> getDeviceModelByManufacturerId(Long manufacturerId) {
        return deviceModelRepository.findByManufacturerId(manufacturerId).stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<DeviceModelResponse> getDeviceModelsByDeviceTypeId(Long deviceTypeId) {
        return deviceModelRepository.findByDeviceTypeId(deviceTypeId).stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<DeviceModelResponse> getAllDeviceModels() {
        return deviceModelRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public void deleteDeviceModel(Long id) {
        if (!deviceModelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Модель устройства с id " + id + " не найдена");
        }
        deviceModelRepository.deleteById(id);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "device-service", 
            "DeviceModel", 
            id, 
            "DELETE",
            "system" //замена
        ));
    }
    
    private DeviceModelResponse mapToResponse(DeviceModel dm) {
        Manufacturer m = dm.getManufacturer();
        DeviceType dt = dm.getDeviceType();
        return new DeviceModelResponse(
            dm.getId(),
            new DeviceModelResponse.ManufacturerInfo(m.getId(), m.getName(), m.getCountry(), m.getUpdatedAt(), m.getCreatedAt()),
            new DeviceModelResponse.DeviceTypeInfo(dt.getId(), dt.getName(), dt.getDescription()),
            dm.getModel(),
            dm.getDescription(),
            dm.getPortsCount(),
            dm.getManagementType(),
            dm.getCreatedAt()
        );
        
    }
}
