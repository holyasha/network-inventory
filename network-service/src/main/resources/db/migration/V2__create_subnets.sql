CREATE TABLE subnets (
    id BIGSERIAL PRIMARY KEY,
    network VARCHAR(50) NOT NULL,
    mask INTEGER NOT NULL,
    gateway VARCHAR(50),
    vlan_id BIGINT,
    description TEXT,

    CONSTRAINT fk_subnet_vlan
        FOREIGN KEY (vlan_id)
        REFERENCES vlans(id)
);