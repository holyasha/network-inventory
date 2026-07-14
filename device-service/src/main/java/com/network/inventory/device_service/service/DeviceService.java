package com.network.inventory.device_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.network.inventory.device_service.dto.request.CreateDeviceRequest;
import com.network.inventory.device_service.dto.request.UpdateDeviceRequest;
import com.network.inventory.device_service.dto.response.DeviceListItemResponse;
import com.network.inventory.device_service.dto.response.DeviceResponse;

public interface DeviceService {
    DeviceResponse createDevice(CreateDeviceRequest request);

    DeviceResponse updateDevice(Long id, UpdateDeviceRequest request);

    DeviceResponse getDeviceById(Long id);

    DeviceResponse getDeviceByInventoryNumber(String inventoryNumber);

    Page<DeviceListItemResponse> getAllDevices(Pageable pageable);

    Page<DeviceListItemResponse> getDevicesByStatus(Long statusId, Pageable pageable);

    Page<DeviceListItemResponse> getDevicesByModel(Long modelId, Pageable pageable);

    void deleteDevice(Long id);
}
