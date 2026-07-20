package com.network.inventory.location_service.service.rack;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.location_service.dto.request.rack.CreateRackRequest;
import com.network.inventory.location_service.dto.request.rack.UpdateRackRequest;
import com.network.inventory.location_service.dto.response.RackResponse;
import com.network.inventory.location_service.entity.Rack;
import com.network.inventory.location_service.entity.Room;
import com.network.inventory.location_service.exeption.ResourceNotFoundException;
import com.network.inventory.location_service.repository.RackRepository;
import com.network.inventory.location_service.repository.RoomRepository;

@Service
@Transactional(readOnly = true)
public class RackServiceImpl implements RackService{

    private final RackRepository rackRepository;
    private final RoomRepository roomRepository;

    
    public RackServiceImpl(RackRepository rackRepository, RoomRepository roomRepository) {
        this.rackRepository = rackRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    @Override
    public RackResponse createRack(CreateRackRequest request) {
        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Помещение с id " + request.roomId() + " не найдено"));
        Rack rack = new Rack(room, request.code(), request.height());
        rack.setManufacturer(request.manufacturer());
        rack.setDescription(request.description());
        return mapToResponse(rackRepository.save(rack));
    }
    
    @Transactional
    @Override
    public RackResponse updateRack(Long id, UpdateRackRequest request) {
        Rack rack = rackRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Стойка с id " + id + " не найдена"));
        if (request.roomId() != null) {
            Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Помещение с id " + request.roomId() + " не найдено"));
            rack.setRoom(room);
        }
        if (request.code() != null) rack.setCode(request.code());
        if (request.height() != null) rack.setHeight(request.height());
        rack.setManufacturer(request.manufacturer());
        rack.setDescription(request.description());
        return mapToResponse(rackRepository.save(rack));
    }

    @Override
    public RackResponse getRackById(Long id) {
        return mapToResponse(rackRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Стойка с id " + id + " не найдена")));
    }

    @Override
    public List<RackResponse> getRackByRoom(Long roomId) {
        return rackRepository.findByRoomId(roomId).stream().map(this::mapToResponse).toList();
    }
    
    @Transactional
    @Override
    public void deleteRack(Long id) {
        if (!rackRepository.existsById(id)) {
            throw new ResourceNotFoundException("Стойка с id " + id + " не найдена");
        }
        rackRepository.deleteById(id);
    }
    
    private RackResponse mapToResponse(Rack r) {
        Room room = r.getRoom();
        return new RackResponse(
            r.getId(),
            new RackResponse.RoomInfo(
                room.getId(),
                new RackResponse.RoomInfo.StationInfo(room.getStation().getId(), room.getStation().getName(), room.getStation().getLine()),
                room.getName()
            ),
            r.getCode(),
            r.getHeight(),
            r.getManufacturer(),
            r.getDescription()
        );
    }
}
