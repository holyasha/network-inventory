package com.network.inventory.network_service.service.networkInterface;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.network_service.dto.event.AuditEventDto;
import com.network.inventory.network_service.dto.request.networkAddress.CreateNetworkInterfaceRequest;
import com.network.inventory.network_service.dto.request.networkAddress.UpdateNetworkInterfaceRequest;
import com.network.inventory.network_service.dto.response.NetworkInterfaceResponse;
import com.network.inventory.network_service.entity.IpAddress;
import com.network.inventory.network_service.entity.NetworkInterface;
import com.network.inventory.network_service.exeption.DuplicateResourceException;
import com.network.inventory.network_service.exeption.ResourceNotFoundException;
import com.network.inventory.network_service.repository.IpAddressRepository;
import com.network.inventory.network_service.repository.NetworkInterfaceRepository;
import com.network.inventory.network_service.service.AuditProducer;

@Service
@Transactional(readOnly = true)
public class NetworkInterfaceServiceImpl implements NetworkInterfaceService{

    private final NetworkInterfaceRepository networkInterfaceRepository;
    private final IpAddressRepository ipAddressRepository;
    private final AuditProducer auditProducer;

    public NetworkInterfaceServiceImpl(NetworkInterfaceRepository networkInterfaceRepository,
            IpAddressRepository ipAddressRepository, AuditProducer auditProducer) {
        this.networkInterfaceRepository = networkInterfaceRepository;
        this.ipAddressRepository = ipAddressRepository;
        this.auditProducer = auditProducer;
    }
    
    @Transactional
    @Override
    public NetworkInterfaceResponse createNetworkInterface(CreateNetworkInterfaceRequest request) {
        if (networkInterfaceRepository.findByMacAddress(request.macAddress()).isPresent()) {
            throw new DuplicateResourceException("Сетевой интерфейс с MAC-адресом " + request.macAddress() + " уже существует");
        }

        NetworkInterface networkInterface = new NetworkInterface(request.deviceId(), request.macAddress());
        networkInterface.setHostname(request.hostname());
        networkInterface.setSpeed(request.speed());
        networkInterface.setDuplex(request.duplex());
        networkInterface.setEnabled(request.enabled() != null ? request.enabled() : true);

        if (request.ipAddressId() != null) {
            IpAddress ipAddress = ipAddressRepository.findById(request.ipAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("IP-адрес с id " + request.ipAddressId() + " не найден"));
            networkInterface.setIpAddress(ipAddress);
        }

        auditProducer.sendAuditEvent(new AuditEventDto(
            "network-service",
            "NetworkInterface",
            networkInterface.getId(),
            "CREATE",
            "system"//замена
        ));
        return mapToResponse(networkInterfaceRepository.save(networkInterface));
    }

    @Transactional
    @Override
    public NetworkInterfaceResponse updateNetworkInterface(Long id, UpdateNetworkInterfaceRequest request) {
        NetworkInterface networkInterface = networkInterfaceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Сетевой интерфейс с id " + id + " не найден"));

        if (request.deviceId() != null) networkInterface.setDeviceId(request.deviceId());

        if (request.macAddress() != null) {
            networkInterfaceRepository.findByMacAddress(request.macAddress()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new DuplicateResourceException("Сетевой интерфейс с MAC-адресом " + request.macAddress() + " уже существует");
                }
            });
            networkInterface.setMacAddress(request.macAddress());
        }

        networkInterface.setHostname(request.hostname());
        networkInterface.setSpeed(request.speed());
        networkInterface.setDuplex(request.duplex());
        if (request.enabled() != null) networkInterface.setEnabled(request.enabled());

        if (request.ipAddressId() != null) {
            IpAddress ipAddress = ipAddressRepository.findById(request.ipAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("IP-адрес с id " + request.ipAddressId() + " не найден"));
            networkInterface.setIpAddress(ipAddress);
        }

        auditProducer.sendAuditEvent(new AuditEventDto(
            "network-service",
            "NetworkInterface",
            id,
            "UPDATE",
            "system"//замена
        ));
        return mapToResponse(networkInterfaceRepository.save(networkInterface));
    }   

    @Override
    public NetworkInterfaceResponse getNetworkInterfaceById(Long id) {
        return mapToResponse(networkInterfaceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Сетевой интерфейс с id: " + id + " не найден")));
    }

    @Override
    public NetworkInterfaceResponse getNetworkInterfaceByMacAddress(String macAddress) {
        return mapToResponse(networkInterfaceRepository.findByMacAddress(macAddress)
            .orElseThrow(() -> new ResourceNotFoundException("Сетевой интерфейс с MAC-адресом: " + macAddress + " не найден")));
    }

    @Override
    public List<NetworkInterfaceResponse> getNetworkInterfacesByDevice(Long deviceId) {
        return networkInterfaceRepository.findByDeviceId(deviceId).stream().map(this::mapToResponse).toList();
    }

    @Transactional
    @Override
    public void deleteNetworkInterface(Long id) {
        if (!networkInterfaceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Сетевой интерфейс не найден с id: " + id);
        }
        networkInterfaceRepository.deleteById(id);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "network-service",
            "NetworkInterface",
            id,
            "DELETE",
            "system"//замена
        ));
    }

    private NetworkInterfaceResponse mapToResponse(NetworkInterface ni) {
        IpAddress ip = ni.getIpAddress();
        return new NetworkInterfaceResponse(
                ni.getId(),
                ni.getDeviceId(),
                ni.getHostname(),
                ni.getMacAddress(),
                ip != null ? new NetworkInterfaceResponse.IpAddressInfo(
                    ip.getId(),
                    ip.getAddress(),
                    ip.getSubnet().getNetwork()
                ) : null,
                ni.getSpeed(),
                ni.getDuplex(),
                ni.getEnabled()
        );
    }    
}
