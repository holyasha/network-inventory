package com.network.inventory.device_service.service.deviceType;

import java.util.List;

import com.network.inventory.device_service.dto.request.deviceType.CreateDeviceTypeRequest;
import com.network.inventory.device_service.dto.request.deviceType.UpdateDeviceTypeRequest;
import com.network.inventory.device_service.dto.response.DeviceTypeResponse;

public interface DeviceTypeService {
    
    DeviceTypeResponse createDeviceType(CreateDeviceTypeRequest request);

    DeviceTypeResponse updateDeviceType( Long id,UpdateDeviceTypeRequest request);

    DeviceTypeResponse getDeviceTypeById(Long id);

    DeviceTypeResponse getDeviceTypeByName(String name);

    List<DeviceTypeResponse> getAllDeviceTypes();

    void deleteDeviceType(Long id);
}
