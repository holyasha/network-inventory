package com.network.inventory.location_service.service.rack_position;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.location_service.dto.request.rack_position.CreateRackPositionRequest;
import com.network.inventory.location_service.dto.request.rack_position.UpdateRackPositionRequest;
import com.network.inventory.location_service.dto.response.RackPositionResponse;
import com.network.inventory.location_service.entity.Rack;
import com.network.inventory.location_service.entity.RackPosition;
import com.network.inventory.location_service.exeption.ResourceNotFoundException;
import com.network.inventory.location_service.repository.RackPositionRepository;
import com.network.inventory.location_service.repository.RackRepository;

@Service
@Transactional(readOnly = true)
public class RackPositionServiceImpl implements RackPositionService {

    private final RackPositionRepository positionRepository;
    private final RackRepository rackRepository;

    
    public RackPositionServiceImpl(RackPositionRepository positionRepository, RackRepository rackRepository) {
        this.positionRepository = positionRepository;
        this.rackRepository = rackRepository;
    }
    @Transactional
    @Override
    public RackPositionResponse createRackPosition(CreateRackPositionRequest request) {
        Rack rack = rackRepository.findById(request.rackId())
            .orElseThrow(() -> new ResourceNotFoundException("Стойка с id " + request.rackId() + " не найдена"));
        RackPosition rackPosition = new RackPosition(rack, request.positionU(), request.occupied() != null ? request.occupied() : false);
        return mapToResponse(positionRepository.save(rackPosition));
    }
    @Transactional
    @Override
    public RackPositionResponse updateRackPosition(Long id, UpdateRackPositionRequest request) {
        RackPosition rackPosition = positionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Позиция стойки с id " + id + " не найдена"));
        if(request.occupied() != null) rackPosition.setOccupied(request.occupied());
        if(request.positionU() != null) rackPosition.setPositionU(request.positionU());
        return mapToResponse(positionRepository.save(rackPosition));
    }

    @Override
    public RackPositionResponse getRackPositionById(Long id) {
        return mapToResponse(positionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Позиция стойки с id " + id + " не найдена")));
    }

    @Override
    public List<RackPositionResponse> getRackPositionsByRack(Long rackId) {
        return positionRepository.findByRackId(rackId).stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<RackPositionResponse> getAvailablePositions(Long rackId) {
        return positionRepository.findByRackIdAndOccupied(rackId, false).stream().map(this::mapToResponse).toList();
    }

    @Transactional
    @Override
    public void deleteRackPosition(Long id) {
        if (!positionRepository.existsById(id)) throw new ResourceNotFoundException("Позиция стойки с id " + id + " не найдена");
        positionRepository.deleteById(id);
    }

    private final RackPositionResponse mapToResponse(RackPosition r) {
        return new RackPositionResponse(
        r.getId(),
        new RackPositionResponse.RackInfo(
            r.getRack().getId(),
            new RackPositionResponse.RackInfo.RoomInfo(
                r.getRack().getRoom().getId(),
                r.getRack().getRoom().getStation().getId(),
                r.getRack().getRoom().getName()
            ),
            r.getRack().getCode()
        ),
        r.getPositionU(),
        r.getOccupied()
    );
    }
}
