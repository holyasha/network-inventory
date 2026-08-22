package com.network.inventory.device_service.service.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.device_service.dto.event.AuditEventDto;
import com.network.inventory.device_service.dto.request.device.CreateDeviceRequest;
import com.network.inventory.device_service.dto.request.device.UpdateDeviceRequest;
import com.network.inventory.device_service.dto.response.DeviceListItemResponse;
import com.network.inventory.device_service.dto.response.DeviceResponse;
import com.network.inventory.device_service.entity.Device;
import com.network.inventory.device_service.entity.DeviceModel;
import com.network.inventory.device_service.entity.DeviceStatus;
import com.network.inventory.device_service.exeption.DuplicateResourceException;
import com.network.inventory.device_service.exeption.ResourceNotFoundException;
import com.network.inventory.device_service.repository.DeviceModelRepository;
import com.network.inventory.device_service.repository.DeviceRepository;
import com.network.inventory.device_service.repository.DeviceStatusRepository;
import com.network.inventory.device_service.service.AuditProducer;

@Service
@Transactional(readOnly = true)
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository deviceRepository;
    private final DeviceModelRepository modelRepository;
    private final DeviceStatusRepository statusRepository;
    private final AuditProducer auditProducer;

    public DeviceServiceImpl(DeviceRepository deviceRepository, DeviceModelRepository modelRepository,
            DeviceStatusRepository statusRepository, AuditProducer auditProducer) {
        this.deviceRepository = deviceRepository;
        this.modelRepository = modelRepository;
        this.statusRepository = statusRepository;
        this.auditProducer = auditProducer;
    }

    @Override
    @Transactional
    public DeviceResponse createDevice(CreateDeviceRequest request) {
        if(deviceRepository.findByInventoryNumber(request.inventoryNumber()).isPresent()) {
            throw new DuplicateResourceException("Устройство с инвентарным номером " + request.inventoryNumber() + " уже существует");
        }
        if (deviceRepository.findBySerialNumber(request.serialNumber()).isPresent()) {
            throw new DuplicateResourceException("Устройство с серийным номером " + request.serialNumber() + " уже существует");
        }

        DeviceModel deviceModel = modelRepository.findById(request.deviceModelId())
            .orElseThrow(() -> new ResourceNotFoundException("Модель устройства с id " + request.deviceModelId() + " не найдена"));
        DeviceStatus status = statusRepository.findById(request.statusId())
            .orElseThrow(() -> new ResourceNotFoundException("Статус устройства с id" + request.statusId() + " не найден"));
        
        Device device = new Device(
            request.inventoryNumber(),
            request.serialNumber(),
            deviceModel,
            status
        );
        device.setPurchaseDate(request.purchaseDate());
        device.setInstallationDate(request.installationDate());
        device.setWarrantyUntil(request.warrantyUntil());
        device.setComment(request.comment());

        Device saved = deviceRepository.save(device);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "device-service",
            "Device",
            saved.getId(),
            "CREATE",
            "system" // замена
        ));
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public DeviceResponse updateDevice(Long id, UpdateDeviceRequest request) {
        Device device = deviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Устройство с id " + id + " не найдено"));
        //обновление инвентарного номера
        if(request.inventoryNumber() != null) {
            deviceRepository.findByInventoryNumber(request.inventoryNumber())
                .ifPresent(existing -> {
                    if(!existing.getId().equals(id)) {
                        throw new DuplicateResourceException("Устройство с инвентарным номером " + request.inventoryNumber() + " уже существует");
                    }
                });
            device.setInventoryNumber(request.inventoryNumber());
        }

        //обновление серийного номера
        if(request.serialNumber() != null) {
            deviceRepository.findBySerialNumber(request.serialNumber())
                .ifPresent(existing -> {
                    if(!existing.getId().equals(id)) {
                        throw new DuplicateResourceException("Устройство с серийным номером " + request.serialNumber() + " уже существует");
                    }
                });
            device.setSerialNumber(request.serialNumber());
        }

        //обновление модели устройства
        if(request.deviceModelId() != null) {
           DeviceModel deviceModel = modelRepository.findById(request.deviceModelId())
                .orElseThrow(() -> new ResourceNotFoundException("Модель устройство с id " + request.deviceModelId() + " не найдено"));
                device.setDeviceModel(deviceModel);
        }

        //обновление статуса
        DeviceStatus status = statusRepository.findById(request.statusId())
        .orElseThrow(() -> new ResourceNotFoundException("Статус устройства с id " + request.statusId() + " не найден"));
        device.setStatus(status);

        device.setPurchaseDate(request.purchaseDate());
        device.setInstallationDate(request.installationDate());
        device.setWarrantyUntil(request.warrantyUntil());
        device.setComment(request.comment());

        Device updated = deviceRepository.save(device);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "device-service",
            "Device",
            id,
            "UPDATE",
            "system" //замена
        ));
        return mapToResponse(updated);
    }

    @Override
    public DeviceResponse getDeviceById(Long id) {
        Device device = deviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Устройство с id " + id + " не найдено"));
        return mapToResponse(device);
    }

    @Override
    public DeviceResponse getDeviceByInventoryNumber(String inventoryNumber) {
        Device device = deviceRepository.findByInventoryNumber(inventoryNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Устройство с интвентарным номером " + inventoryNumber + " не найдено"));
        return mapToResponse(device);
    }

    @Override
    public Page<DeviceListItemResponse> getAllDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable)
            .map(this::mapToListItem);
    }

    @Override
    public Page<DeviceListItemResponse> getDevicesByStatus(Long statusId, Pageable pageable) {
        return deviceRepository.findByStatusId(statusId, pageable)
            .map(this::mapToListItem);
    }

    @Override
    public Page<DeviceListItemResponse> getDevicesByModel(Long modelId, Pageable pageable) {
        return deviceRepository.findByDeviceModelId(modelId, pageable)
            .map(this::mapToListItem);
    }

    @Override
    @Transactional
    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Устройство с id " + id + " не найдено");
        }
        deviceRepository.deleteById(id);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "device-service",
            "Device",
            id, "DELETE",
            "system" //замена
        ));
    }

    private DeviceResponse mapToResponse(Device device) {
        DeviceModel model = device.getDeviceModel();
        return new DeviceResponse(
            device.getId(),
            device.getInventoryNumber(),
            device.getSerialNumber(),
            new DeviceResponse.DeviceModelInfo(
                model.getId(),
                model.getModel(),
                model.getManufacturer().getName(),
                model.getDeviceType().getName()
            ),
            new DeviceResponse.DeviceStatusInfo(
                device.getStatus().getId(),
                device.getStatus().getName()
            ),
            device.getPurchaseDate(),
            device.getInstallationDate(),
            device.getWarrantyUntil(),
            device.getComment(),
            device.getCreatedAt(),
            device.getUpdatedAt()
        );
    }

    private DeviceListItemResponse mapToListItem(Device device) {
        return new DeviceListItemResponse(
            device.getId(),
            device.getInventoryNumber(),
            device.getSerialNumber(),
            device.getDeviceModel().getModel(),
            device.getStatus().getName(),
            device.getInstallationDate()
        );
    }
}
