package com.network.inventory.device_service.service.manufacturer;

import java.util.List;

import com.network.inventory.device_service.dto.request.manufacturer.CreateManufacturerRequest;
import com.network.inventory.device_service.dto.request.manufacturer.UpdateManufacturerRequest;
import com.network.inventory.device_service.dto.response.ManufacturerResponse;

public interface ManufacturerService {
    ManufacturerResponse createManufacturer(CreateManufacturerRequest request);

    ManufacturerResponse updateManufacturer(Long id, UpdateManufacturerRequest request);

    ManufacturerResponse getManufacturerById(Long id);

    ManufacturerResponse getManufacturerByName(String name);

    List<ManufacturerResponse> getAllManufacturers();

    void deleteManufacturer(Long id);
}
