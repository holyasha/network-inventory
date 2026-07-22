package com.network.inventory.device_service.service.manufacturer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.device_service.dto.request.manufacturer.CreateManufacturerRequest;
import com.network.inventory.device_service.dto.request.manufacturer.UpdateManufacturerRequest;
import com.network.inventory.device_service.dto.response.ManufacturerResponse;
import com.network.inventory.device_service.entity.Manufacturer;
import com.network.inventory.device_service.exeption.DuplicateResourceException;
import com.network.inventory.device_service.exeption.ResourceNotFoundException;
import com.network.inventory.device_service.repository.ManufacturerRepository;

@Service
@Transactional(readOnly = true)
public class ManufacturerServiceImpl implements ManufacturerService{

    private final ManufacturerRepository manufacturerRepository;

    
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Transactional
    @Override
    public ManufacturerResponse createManufacturer(CreateManufacturerRequest request) {
        if (manufacturerRepository.findByName(request.name()).isPresent()) {
            throw new DuplicateResourceException("Производитель с наименованием " + request.name() + " уже существует");
        }
        Manufacturer saved = manufacturerRepository.save(new Manufacturer(request.name(),request.country()));
        return mapToResponse(saved);
    }

    @Transactional
    @Override
    public ManufacturerResponse updateManufacturer(Long id, UpdateManufacturerRequest request) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Производитель с id " + id + " не найден"));
        if (manufacturerRepository.findByName(request.name()).isPresent()) {
            throw new DuplicateResourceException("Производитель с наименованием " + request.name() + " уже существует");
        }
        manufacturer.setName(request.name());
        if (request.country()!=null) manufacturer.setCountry(request.country());
       Manufacturer saved = manufacturerRepository.save(manufacturer);
       return mapToResponse(saved); 
    }

    @Override
    public ManufacturerResponse getManufacturerById(Long id) {
        return mapToResponse(manufacturerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Производитель с id " + id + " не найден")));
    }

    @Override
    public ManufacturerResponse getManufacturerByName(String name) {
        return mapToResponse(manufacturerRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Производитель с наименованием " + name + " не найден")));
    }

    @Override
    public List<ManufacturerResponse> getAllManufacturers() {
        return manufacturerRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Transactional
    @Override
    public void deleteManufacturer(Long id) {
        if (!manufacturerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Производитель с id " + id + " не найден");
        }
        manufacturerRepository.deleteById(id);
    }

    private ManufacturerResponse mapToResponse(Manufacturer m) {
        return new ManufacturerResponse(
            m.getId(),
            m.getName(),
            m.getCountry(),
            m.getUpdatedAt(),
            m.getCreatedAt()
        );
    }
    
}
