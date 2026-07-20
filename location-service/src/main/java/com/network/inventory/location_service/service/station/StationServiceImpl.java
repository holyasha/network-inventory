package com.network.inventory.location_service.service.station;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.location_service.dto.request.station.CreateStationRequest;
import com.network.inventory.location_service.dto.request.station.UpdateStationRequest;
import com.network.inventory.location_service.dto.response.StationResponse;
import com.network.inventory.location_service.entity.Station;
import com.network.inventory.location_service.exeption.DuplicateResourceException;
import com.network.inventory.location_service.exeption.ResourceNotFoundException;
import com.network.inventory.location_service.repository.StationRepository;

@Service
@Transactional(readOnly = true)
public class StationServiceImpl implements StationService{

    private final StationRepository stationRepository;
    
    
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Transactional
    @Override
    public StationResponse createStation(CreateStationRequest request) {
        if(stationRepository.findByLineAndName(request.name(), request.line()).isPresent()) {
            throw new DuplicateResourceException("Станция " + request.name() + " уже существует");
        }
        Station station = new Station(request.name(), request.line(), request.address());
        station.setDescription(request.description());
        return mapToResponse(stationRepository.save(station));
    }

    @Transactional
    @Override
    public StationResponse updateStation(Long id, UpdateStationRequest request) {
        Station station = stationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Станция с id " + id + " не найдена"));
        if((request.name() != null) && (request.line() != null )) {
            stationRepository.findByLineAndName(request.name(), request.line()).ifPresent(existing -> {
                if(!existing.getId().equals(id)) throw new DuplicateResourceException("Станция " + request.name() +" уже сущестует");
            });
        }
        station.setName(request.name());
        if(request.line() != null) station.setLine(request.line());
        station.setAdress(request.address());
        station.setDescription(request.description());
        return mapToResponse(stationRepository.save(station));
        }

    @Override
    public StationResponse getStationById(Long id) {
        return mapToResponse(stationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Станция с id " + id + " не найдена")));
    }

    @Override
    public List<StationResponse> getStationByName(String name) {
        return stationRepository.findByName(name)
            .stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<StationResponse> getAllStations() {
        return stationRepository.findAll()
            .stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<StationResponse> getStatiionsByLine(String line) {
        return stationRepository.findByLine(line)
            .stream().map(this::mapToResponse).toList();
    }
    @Transactional
    @Override
    public void deleteStation(Long id) {
        if (!stationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Станция с id " + id + " не найдена");
        }
        stationRepository.deleteById(id);
    }

    public StationResponse mapToResponse(Station s) {
        return new StationResponse(s.getId(), s.getName(), s.getLine(), s.getAdress(), s.getDescription());
    }
    
}
