package com.network.inventory.device_service.service.deviceStatus;

import java.util.List;

import com.network.inventory.device_service.dto.request.deviceStatus.CreateDeviceStatusRequest;
import com.network.inventory.device_service.dto.request.deviceStatus.UpdateDeviceStatusRequest;
import com.network.inventory.device_service.dto.response.DeviceStatusResponse;

public interface DeviceStatusService {
    DeviceStatusResponse createDeviceStatus(CreateDeviceStatusRequest request);

    DeviceStatusResponse updateDeviceStatus(Long id, UpdateDeviceStatusRequest request);

    DeviceStatusResponse getDeviceStatusById(Long id);

    DeviceStatusResponse getDeviceStatusByName(String name);

    List<DeviceStatusResponse> getDeviceStatusesByColor(String color);

    List<DeviceStatusResponse> getAllStatuses();

    void deleteDevieStatus(Long id);
}
