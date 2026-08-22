package com.network.inventory.location_service.service.room;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.location_service.dto.event.AuditEventDto;
import com.network.inventory.location_service.dto.request.room.CreateRoomRequest;
import com.network.inventory.location_service.dto.request.room.UpdateRoomRequest;
import com.network.inventory.location_service.dto.response.RoomResponse;
import com.network.inventory.location_service.entity.Room;
import com.network.inventory.location_service.entity.Station;
import com.network.inventory.location_service.exeption.ResourceNotFoundException;
import com.network.inventory.location_service.repository.RoomRepository;
import com.network.inventory.location_service.repository.StationRepository;
import com.network.inventory.location_service.service.AuditProducer;

@Service
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final StationRepository stationRepository;
    private final AuditProducer auditProducer;

    

    public RoomServiceImpl(RoomRepository roomRepository, 
        StationRepository stationRepository, AuditProducer auditProducer) {
        this.roomRepository = roomRepository;
        this.stationRepository = stationRepository;
        this.auditProducer = auditProducer;
    }

    @Transactional
    @Override
    public RoomResponse createRoom(CreateRoomRequest request) {
        Station station = stationRepository.findById(request.stationId())
            .orElseThrow(() -> new ResourceNotFoundException("Станция с id" + request.stationId() + " не найдена"));
        Room room = new Room(station, request.name(), request.type());
        room.setFloor(request.floor());
        room.setDescription(request.description());

        auditProducer.sendAuditEvent(new AuditEventDto(
            "location-service",
            "Room",
            room.getId(),
            "CREATE",
            "system"//замена
        ));
        return mapToResponse(roomRepository.save(room));
    }
    @Transactional
    @Override
    public RoomResponse updateRoom(Long id, UpdateRoomRequest request) {
        Room room = roomRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Помещение с id " + id + " не найдено"));
        if (request.stationId()!=null) {
            Station station = stationRepository.findById(request.stationId())
                .orElseThrow(() -> new ResourceNotFoundException("Станция с id " + request.stationId() + " не найдена"));
            room.setStation(station);
        }
        if (request.name()!=null) room.setName(request.name());
        if (request.type()!=null) room.setType(request.type());
        room.setFloor(request.floor());
        room.setDescription(request.description());

        auditProducer.sendAuditEvent(new AuditEventDto(
            "location-service",
            "Room",
            id,
            "UPDATE",
            "system"//замена
        ));
        return mapToResponse(roomRepository.save(room));
    }

    @Override
    public RoomResponse getRoomById(Long id) {
        return mapToResponse(roomRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Помещение с id " + id + " не найдено")));
    }

    @Override
    public List<RoomResponse> getRoomsByStation(Long stationId) {
        return roomRepository.findByStationId(stationId).stream().map(this::mapToResponse).toList();
    }

    @Transactional
    @Override
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Помещение с id " + id + " не найдено");
        }
        roomRepository.deleteById(id);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "location-service",
            "Room",
            id,
            "DELETE",
            "system"//замена
        ));
    }
    
    private RoomResponse mapToResponse(Room r) {
        return new RoomResponse(
            r.getId(),
            new RoomResponse.StationInfo(r.getStation().getId(), r.getStation().getName(), r.getStation().getLine()),
            r.getName(),
            r.getType(),
            r.getFloor(),
            r.getDescription()
        );
    }
}
