package com.network.inventory.device_service.service.deviceModel;

import java.util.List;

import com.network.inventory.device_service.dto.request.deviceModel.CreateDeviceModelRequest;
import com.network.inventory.device_service.dto.request.deviceModel.UpdateDeviceModelRequest;
import com.network.inventory.device_service.dto.response.DeviceModelResponse;

public interface DeviceModelService {

    DeviceModelResponse createDeviceModel(CreateDeviceModelRequest request);

    DeviceModelResponse updateDeviceModel(Long id, UpdateDeviceModelRequest request);

    DeviceModelResponse getDeviceModelById(Long id);

    List<DeviceModelResponse> getDeviceModelByManufacturerId(Long manufacturerId);

    List<DeviceModelResponse> getDeviceModelsByDeviceTypeId(Long deviceTypeId);

    List<DeviceModelResponse> getAllDeviceModels();

    void deleteDeviceModel(Long id);
    
}
