CREATE TABLE device_locations (
    id BIGSERIAL PRIMARY KEY,
    device_id BIGINT NOT NULL,
    rack_position_id BIGINT NOT NULL,
    installed_at DATE,

    CONSTRAINT fk_location_position
        FOREIGN KEY (rack_position_id)
        REFERENCES rack_positions(id),

    CONSTRAINT uq_device UNIQUE(device_id)
);