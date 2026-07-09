CREATE TABLE network_interfaces (
    id BIGSERIAL PRIMARY KEY,
    device_id BIGINT NOT NULL,
    hostname VARCHAR(100),
    mac_address VARCHAR(17) NOT NULL UNIQUE,
    ip_address_id BIGINT,
    speed INTEGER,
    duplex VARCHAR(20),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_interface_ip
        FOREIGN KEY (ip_address_id)
        REFERENCES ip_addresses(id)
);

CREATE INDEX idx_interface_device
ON network_interfaces(device_id);