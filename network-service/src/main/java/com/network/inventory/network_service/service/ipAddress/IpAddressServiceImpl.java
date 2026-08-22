package com.network.inventory.network_service.service.ipAddress;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.network_service.dto.event.AuditEventDto;
import com.network.inventory.network_service.dto.request.ipAddress.CreateIpAddressRequest;
import com.network.inventory.network_service.dto.request.ipAddress.UpdateIpAddressRequest;
import com.network.inventory.network_service.dto.response.IpAddressResponse;
import com.network.inventory.network_service.entity.IpAddress;
import com.network.inventory.network_service.entity.Subnet;
import com.network.inventory.network_service.exeption.DuplicateResourceException;
import com.network.inventory.network_service.exeption.ResourceNotFoundException;
import com.network.inventory.network_service.repository.IpAddressRepository;
import com.network.inventory.network_service.repository.SubnetRepository;
import com.network.inventory.network_service.service.AuditProducer;

@Service
@Transactional(readOnly = true)
public class IpAddressServiceImpl implements IpAddressService{

    private final IpAddressRepository ipAddressRepository;
    private final SubnetRepository subnetRepository;
    private final AuditProducer auditProducer;

    public IpAddressServiceImpl(IpAddressRepository ipAddressRepository,
        SubnetRepository subnetRepository, AuditProducer auditProducer) {
        this.ipAddressRepository = ipAddressRepository;
        this.subnetRepository = subnetRepository;
        this.auditProducer = auditProducer;
    }

    @Transactional
    @Override
    public IpAddressResponse createIpAddress(CreateIpAddressRequest request) {
        if (ipAddressRepository.findByAddress(request.address()).isPresent()) {
            throw new DuplicateResourceException("IP-адрес " + request.address() + " уже существует");
        }

        Subnet subnet = subnetRepository.findById(request.subnetId())
            .orElseThrow(() -> new ResourceNotFoundException("Подсеть с id " + request.subnetId() + " не найдена"));

        IpAddress ipAddress = new IpAddress(subnet, request.address(),
        request.allocated() != null ? request.allocated() : false,
        request.reserved() != null ? request.reserved() : false);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "network-service",
            "IpAddress",
            ipAddress.getId(),
            "CREATE",
            "system"//замена
        ));
        return mapToResponse(ipAddressRepository.save(ipAddress));
    }

    @Transactional
    @Override
    public IpAddressResponse updateIpAddress(Long id, UpdateIpAddressRequest request) {
        IpAddress ipAddress = ipAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IP-адрес с id " + id + " не найден"));

        if (request.subnetId() != null) {
            Subnet subnet = subnetRepository.findById(request.subnetId())
                    .orElseThrow(() -> new ResourceNotFoundException("Подсеть с id " + request.subnetId() + " не найдена"));
            ipAddress.setSubnet(subnet);
        }

        if (request.address() != null) {
            ipAddressRepository.findByAddress(request.address()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new DuplicateResourceException("IP-адрес " + request.address() + " уже существует");
                }
            });
            ipAddress.setAddress(request.address());
        }

        if (request.allocated() != null) ipAddress.setAllocated(request.allocated());
        if (request.reserved() != null) ipAddress.setReserved(request.reserved());

        auditProducer.sendAuditEvent(new AuditEventDto(
            "network-service",
            "IpAddress",
            id,
            "UPDATE",
            "system"//замена
        ));
        return mapToResponse(ipAddressRepository.save(ipAddress));
    }

    @Override
    public IpAddressResponse getIpAddressById(Long id) {
        return mapToResponse(ipAddressRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("IP-адрес с id " + id + " не найден")));
    }

    @Override
    public IpAddressResponse getIpAddressByAddress(String address) {
        return mapToResponse(ipAddressRepository.findByAddress(address)
            .orElseThrow(() -> new ResourceNotFoundException("IP-адрес " + address + " не найден")));
    }

    @Override
    public Page<IpAddressResponse> getIpAddressesBySubnet(Long subnetId, Pageable pageable) {
        return ipAddressRepository.findBySubnetId(subnetId, pageable).map(this::mapToResponse);
    }

    @Override
    public Page<IpAddressResponse> getAvailableIpAddresses(Long subnetId, Pageable pageable) {
        return ipAddressRepository.findBySubnetIdAndAllocated(subnetId, false, pageable).map(this::mapToResponse);
    }

    @Transactional
    @Override
    public void deleteIpAddress(Long id) {
        if (!ipAddressRepository.existsById(id)) {
            throw new ResourceNotFoundException("IP-адрес с id " + id + " не найден");
        }
        ipAddressRepository.deleteById(id);

        auditProducer.sendAuditEvent(new AuditEventDto(
            "network-service",
            "IpAddress",
            id,
            "DELETE",
            "system"//замена
        ));
    }
    
    private IpAddressResponse mapToResponse(IpAddress ip) {
          Subnet subnet = ip.getSubnet();
          return new IpAddressResponse(
                  ip.getId(),
                  new IpAddressResponse.SubnetInfo(subnet.getId(), subnet.getNetwork(), subnet.getMask()),
                  ip.getAddress(),
                  ip.getAllocated(),
                  ip.getReserved()
          );
      }
}
