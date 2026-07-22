package com.network.inventory.device_service.service.deviceStatus;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.device_service.dto.request.deviceStatus.CreateDeviceStatusRequest;
import com.network.inventory.device_service.dto.request.deviceStatus.UpdateDeviceStatusRequest;
import com.network.inventory.device_service.dto.response.DeviceStatusResponse;
import com.network.inventory.device_service.entity.DeviceStatus;
import com.network.inventory.device_service.exeption.DuplicateResourceException;
import com.network.inventory.device_service.exeption.ResourceNotFoundException;
import com.network.inventory.device_service.repository.DeviceStatusRepository;

@Service
@Transactional(readOnly = true)
public class DeviceStatusServiceImpl implements DeviceStatusService{

    private final DeviceStatusRepository deviceStatusRepository;

    

    public DeviceStatusServiceImpl(DeviceStatusRepository deviceStatusRepository) {
        this.deviceStatusRepository = deviceStatusRepository;
    }

    @Transactional
    @Override
    public DeviceStatusResponse createDeviceStatus(CreateDeviceStatusRequest request) {
        if(deviceStatusRepository.findByName(request.name()).isPresent()) {
            throw new DuplicateResourceException("Статус с наименованием " + request.name() + " уже существует");
        }
        DeviceStatus saved = deviceStatusRepository.save(new DeviceStatus(request.name(), request.color()));
        return mapToResponse(saved);
    }

    @Transactional
    @Override
    public DeviceStatusResponse updateDeviceStatus(Long id, UpdateDeviceStatusRequest request) {
        DeviceStatus deviceStatus = deviceStatusRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Статус устройства с id " + id + " не найден"));
        
        if(deviceStatusRepository.findByName(request.name()).isPresent()) {
            throw new DuplicateResourceException("Статус с наименованием " + request.name() + " уже существует");
        }
        deviceStatus.setName(request.name());
        deviceStatus.setColor(request.color());
        DeviceStatus saved = deviceStatusRepository.save(deviceStatus);
        return mapToResponse(saved);
    }

    @Override
    public DeviceStatusResponse getDeviceStatusById(Long id) {
        return mapToResponse(deviceStatusRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Статус с id " + id + " не найден")));
    }

    @Override
    public DeviceStatusResponse getDeviceStatusByName(String name) {
        return mapToResponse(deviceStatusRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Статус с наименованием " + name + " не найден")));
    }

    @Override
    public List<DeviceStatusResponse> getDeviceStatusesByColor(String color) {
        return deviceStatusRepository.findByColor(color).stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<DeviceStatusResponse> getAllStatuses() {
        return deviceStatusRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Transactional
    @Override
    public void deleteDevieStatus(Long id) {
        if(!deviceStatusRepository.existsById(id)) {
            throw new ResourceNotFoundException("Статус с id " + id + " не найден");
        }
        deviceStatusRepository.deleteById(id);
    }

    private DeviceStatusResponse mapToResponse(DeviceStatus d) {
        return new DeviceStatusResponse(d.getId(), d.getName(), d.getColor());
    }
    
}
