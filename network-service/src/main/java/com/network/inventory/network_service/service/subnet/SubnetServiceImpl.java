package com.network.inventory.network_service.service.subnet;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.network_service.dto.event.AuditEventDto;
import com.network.inventory.network_service.dto.request.subnet.CreateSubnetRequest;
import com.network.inventory.network_service.dto.request.subnet.UpdateSubnetRequest;
import com.network.inventory.network_service.dto.response.SubnetResponse;
import com.network.inventory.network_service.entity.Subnet;
import com.network.inventory.network_service.entity.Vlan;
import com.network.inventory.network_service.exeption.ResourceNotFoundException;
import com.network.inventory.network_service.repository.SubnetRepository;
import com.network.inventory.network_service.repository.VlanRepository;
import com.network.inventory.network_service.service.AuditProducer;

@Service
@Transactional(readOnly = true)
public class SubnetServiceImpl implements SubnetService{
    
    private final SubnetRepository subnetRepository;
    private final VlanRepository vlanRepository;
    private final AuditProducer auditProducer;

    public SubnetServiceImpl(SubnetRepository subnetRepository,
        VlanRepository vlanRepository, AuditProducer auditProducer) {
        this.subnetRepository = subnetRepository;
        this.vlanRepository = vlanRepository;
        this.auditProducer = auditProducer;
    }

    @Transactional
    @Override
    public SubnetResponse createSubnet(CreateSubnetRequest request) {
        Subnet subnet = new Subnet(request.network(), request.mask());
        subnet.setGateway(request.gateway());
        subnet.setDescription(request.description());

        if (request.vlanId() != null) {
            Vlan vlan = vlanRepository.findById(request.vlanId())
                    .orElseThrow(() -> new ResourceNotFoundException("VLAN с id " + request.vlanId() + " не найден"));
            subnet.setVlan(vlan);
        }

        auditProducer.sendAuditEvent(new AuditEventDto(
            "network-service",
            "Subnet",
            subnet.getId(),
            "CREATE",
            "system"//замена
        ));
        return mapToResponse(subnetRepository.save(subnet));
    }

    @Transactional
    @Override
    public SubnetResponse updateSubnet(Long id, UpdateSubnetRequest request) {
        Subnet subnet = subnetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Подсеть с id " + id + " не найдена"));
        if (request.network() != null) subnet.setNetwork(request.network());
        if (request.mask() != null) subnet.setMask(request.mask());
        subnet.setGateway(request.gateway());
        subnet.setDescription(request.description());

        if (request.vlanId() != null) {
            Vlan vlan = vlanRepository.findById(request.vlanId())
                .orElseThrow(() -> new ResourceNotFoundException("VLAN с id " + request.vlanId() + " не найден"));
            subnet.setVlan(vlan);
        }

        auditProducer.sendAuditEvent(new AuditEventDto(
            "network-service",
            "Subnet",
            id,
            "UPDATE",
            "system"//замена
        ));
        return mapToResponse(subnetRepository.save(subnet));
    }

    @Override
    public SubnetResponse getSubnetById(Long id) {
        return mapToResponse(subnetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Подсеть с id " + id + " не найдена")));
    }

    @Override
    public List<SubnetResponse> getSubnetsByVlan(Long vlanId) {
        return subnetRepository.findByVlanId(vlanId).stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<SubnetResponse> getAllSubnets() {
        return subnetRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public void deleteSubnet(Long id) {
        if(!subnetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Подсеть с id " + id + " не найдена");
        }
        subnetRepository.deleteById(id);
        auditProducer.sendAuditEvent(new AuditEventDto(
            "network-service",
            "Subnet",
            id,
            "DELETE",
            "system"//замена
        ));
    }

    private SubnetResponse mapToResponse(Subnet s) {
        Vlan vlan = s.getVlan();
        return new SubnetResponse(
            s.getId(),
            s.getNetwork(),
            s.getMask(),
            s.getGateway(),
            vlan != null ? new SubnetResponse.VlanInfo(vlan.getId(), vlan.getNumber(), vlan.getName()) : null,
            s.getDescription()
        );
    }
}
