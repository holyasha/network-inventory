package com.network.inventory.location_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "rack_positions", uniqueConstraints = {
    @UniqueConstraint(name = "uq_positions", columnNames = {"rack_id", "position_u"})
})
public class RackPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rack_id", nullable = false)
    private Rack rack;

    @Column(name = "position_u", nullable = false)
    private Integer positionU;

    @Column(nullable = false)
    private Boolean occupied = false;

    public RackPosition() {
    }

    public RackPosition(Rack rack, Integer positionU, Boolean occupied) {
        this.rack = rack;
        this.positionU = positionU;
        this.occupied = occupied;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public Integer getPositionU() {
        return positionU;
    }

    public void setPositionU(Integer positionU) {
        this.positionU = positionU;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    
}
