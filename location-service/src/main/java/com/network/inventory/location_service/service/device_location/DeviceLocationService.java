package com.network.inventory.location_service.service.device_location;

import com.network.inventory.location_service.dto.request.device_location.CreateDeviceLocationRequest;
import com.network.inventory.location_service.dto.request.device_location.UpdateDeviceLocationRequest;
import com.network.inventory.location_service.dto.response.DeviceLocationResponse;

public interface DeviceLocationService {
    DeviceLocationResponse createDeviceLocation(CreateDeviceLocationRequest request);

    DeviceLocationResponse updateDeviceLocation(Long id, UpdateDeviceLocationRequest request);

    DeviceLocationResponse getDeviceLocationById(Long id);

    DeviceLocationResponse getDeviceLocationByDeviceId(Long deviceId);

    void deleteDeviceLocation(Long id);
}
