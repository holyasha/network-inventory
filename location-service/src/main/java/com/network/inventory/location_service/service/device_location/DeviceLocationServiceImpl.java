package com.network.inventory.location_service.service.device_location;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.location_service.dto.request.device_location.CreateDeviceLocationRequest;
import com.network.inventory.location_service.dto.request.device_location.UpdateDeviceLocationRequest;
import com.network.inventory.location_service.dto.response.DeviceLocationResponse;
import com.network.inventory.location_service.entity.DeviceLocation;
import com.network.inventory.location_service.entity.Rack;
import com.network.inventory.location_service.entity.RackPosition;
import com.network.inventory.location_service.exeption.DuplicateResourceException;
import com.network.inventory.location_service.exeption.ResourceNotFoundException;
import com.network.inventory.location_service.repository.DeviceLocationRepository;
import com.network.inventory.location_service.repository.RackPositionRepository;

@Service
@Transactional(readOnly = true)
public class DeviceLocationServiceImpl implements DeviceLocationService {

    private final DeviceLocationRepository deviceLocationRepository;
    private final RackPositionRepository rackPositionRepository;

    
    public DeviceLocationServiceImpl(DeviceLocationRepository deviceLocationRepository,
            RackPositionRepository rackPositionRepository) {
        this.deviceLocationRepository = deviceLocationRepository;
        this.rackPositionRepository = rackPositionRepository;
    }
    
    @Transactional
    @Override
    public DeviceLocationResponse createDeviceLocation(CreateDeviceLocationRequest request) {
        if (deviceLocationRepository.findByDeviceId(request.deviceId()).isPresent()) {
            throw new DuplicateResourceException("Размещение для устройства с id " + request.deviceId() + " уже существует");
        }
        RackPosition rackPosition = rackPositionRepository.findById(request.rackPositionId())
            .orElseThrow(() -> new ResourceNotFoundException("Позиция стойки с id " + request.rackPositionId() + " не найдена"));
        DeviceLocation deviceLocation = new DeviceLocation(request.deviceId(), rackPosition);
        deviceLocation.setInstalledAt(request.installedAt());
        return mapToResponse(deviceLocationRepository.save(deviceLocation));
    }

    @Transactional
    @Override
    public DeviceLocationResponse updateDeviceLocation(Long id, UpdateDeviceLocationRequest request) {
        DeviceLocation deviceLocation = deviceLocationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Размещение для устройства с id" + id + " не найдено"));
        if(request.rackPositionId() != null) {
            RackPosition rackPosition = rackPositionRepository.findById(request.rackPositionId())
                .orElseThrow(() -> new ResourceNotFoundException("Позиция стойки с id " + id + " не найдена"));
            deviceLocation.setRackPosition(rackPosition);
        }
        if (request.installedAt() != null) {
            deviceLocation.setInstalledAt(request.installedAt());
        }
        return mapToResponse(deviceLocationRepository.save(deviceLocation));
    }

    @Override
    public DeviceLocationResponse getDeviceLocationById(Long id) {
        return mapToResponse(deviceLocationRepository.findByDeviceId(id).orElseThrow(() -> new ResourceNotFoundException("Размещение устройства с id " + id + " не найдено")));
    }

    @Override
    public DeviceLocationResponse getDeviceLocationByDeviceId(Long deviceId) {
        return mapToResponse(deviceLocationRepository.findByDeviceId(deviceId).orElseThrow(() -> new ResourceNotFoundException("Размещение устройства с id " + deviceId + " не найдено")));
    }

    @Transactional
    @Override
    public void deleteDeviceLocation(Long id) {
        if(!deviceLocationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Размещение устройства с id " + id + " не найдено");
        }
        deviceLocationRepository.deleteById(id);
    }

    private DeviceLocationResponse mapToResponse(DeviceLocation d) {
        RackPosition rp = d.getRackPosition();
        Rack rack = rp.getRack();
        return new DeviceLocationResponse(
            d.getId(),
            d.getDeviceId(),
            new DeviceLocationResponse.RackPositionInfo(
                rp.getId(),
                new DeviceLocationResponse.RackPositionInfo.RackInfo(
                    rack.getId(),
                    rack.getRoom().getId(),
                    rack.getCode()
                ),
                rp.getPositionU(),
                rp.getOccupied()
            ),
            d.getInstalledAt()
        );
    }
    
}
