package com.network.inventory.device_service.service.deviceModel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
@Transactional(readOnly = true)
public class DeviceModelServiceImpl implements DeviceModelService {

    private final DeviceModelRepository deviceModelRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final DeviceTypeRepository deviceTypeRepository;

    
    public DeviceModelServiceImpl(DeviceModelRepository deviceModelRepository,
            ManufacturerRepository manufacturerRepository, DeviceTypeRepository deviceTypeRepository) {
        this.deviceModelRepository = deviceModelRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.deviceTypeRepository = deviceTypeRepository;
    }

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
        return mapToResponse(saved);
    }

    @Override
    public DeviceModelResponse updateDeviceModel(Long id, UpdateDeviceModelRequest request) {
        DeviceModel deviceModel = deviceModelRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Модель устройства с id " + id + " не найдена"));
        
        if (request.manufacturerId()!= null) {
            
            
        }
    }

    @Override
    public DeviceModelResponse getDeviceModelById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeviceModelById'");
    }

    @Override
    public List<DeviceModelResponse> getDeviceModelByManufacturerId(Long manufacturerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeviceModelByManufacturerId'");
    }

    @Override
    public List<DeviceModelResponse> getDeviceModelsByDeviceTypeId(Long deviceTypeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeviceModelsByDeviceTypeId'");
    }

    @Override
    public List<DeviceModelResponse> getAllDeviceModels() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllDeviceModels'");
    }

    @Override
    public void deleteDeviceModel(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteDeviceModel'");
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
