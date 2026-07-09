CREATE TABLE rack_positions (
    id BIGSERIAL PRIMARY KEY,
    rack_id BIGINT NOT NULL,
    position_u INTEGER NOT NULL,
    occupied BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_position_rack
        FOREIGN KEY (rack_id)
        REFERENCES racks(id),

    CONSTRAINT uq_position UNIQUE(rack_id, position_u)
);