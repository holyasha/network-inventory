package com.network.inventory.network_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.network.inventory.network_service.entity.Subnet;
import java.util.List;

public interface SubnetRepository extends JpaRepository<Subnet, Long> {

    List<Subnet> findByVlanId(Long vlanId);
}
