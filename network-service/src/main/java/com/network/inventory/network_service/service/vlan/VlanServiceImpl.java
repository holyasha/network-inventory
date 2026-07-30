package com.network.inventory.network_service.service.vlan;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.network_service.dto.request.vlan.CreateVlanRequest;
import com.network.inventory.network_service.dto.request.vlan.UpdateVlanRequest;
import com.network.inventory.network_service.dto.response.VlanResponse;
import com.network.inventory.network_service.entity.Vlan;
import com.network.inventory.network_service.exeption.DuplicateResourceException;
import com.network.inventory.network_service.exeption.ResourceNotFoundException;
import com.network.inventory.network_service.repository.VlanRepository;

@Service
@Transactional(readOnly = true)
public class VlanServiceImpl implements VlanService{
    
    private final VlanRepository vlanRepository;

    public VlanServiceImpl(VlanRepository vlanRepository) {
        this.vlanRepository = vlanRepository;
    }

    @Transactional
    @Override
    public VlanResponse createVlan(CreateVlanRequest request) {
        if (vlanRepository.findByNumber(request.number()).isPresent()) {
            throw new DuplicateResourceException("VLAN с номером " + request.number() + " уже существует");
        }
        Vlan vlan = new Vlan(request.number());
        vlan.setName(request.name());
        vlan.setDescription(request.description());
        return mapToResponse(vlanRepository.save(vlan));
    }

    @Transactional
    @Override
    public VlanResponse updateVlan(Long id, UpdateVlanRequest request) {
        Vlan vlan = vlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VLAN c id " + id + " не найден"));
        if (request.number() != null) {
            vlanRepository.findByNumber(request.number()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new DuplicateResourceException("VLAN с номером " + request.number() + " уже существует");
            }
            });
            vlan.setNumber(request.number());
        }
        vlan.setName(request.name());
        vlan.setDescription(request.description());
        return mapToResponse(vlanRepository.save(vlan));
    }

    @Override
    public VlanResponse getVlanById(Long id) {
        return mapToResponse(vlanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("VLAN с id " + id + " не найден")));
    }

    @Override
    public VlanResponse getVlanByNumber(Integer number) {
        return mapToResponse(vlanRepository.findByNumber(number)
            .orElseThrow(() -> new ResourceNotFoundException("VLAN с номером " + number + " не найден")));
    }

    @Override
    public List<VlanResponse> getAllVlans() {
        return vlanRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Transactional
    @Override
    public void deleteVlan(Long id) {
        if (!vlanRepository.existsById(id)) {
            throw new ResourceNotFoundException("VLAN с id " + id + " не найден");
        }
        vlanRepository.deleteById(id);
    }

    private VlanResponse mapToResponse(Vlan v) {
        return new VlanResponse(v.getId(), v.getNumber(), v.getName(), v.getDescription());
    }
}
