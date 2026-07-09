CREATE TABLE racks (
    id BIGSERIAL PRIMARY KEY,
    room_id BIGINT NOT NULL,
    code VARCHAR(50) NOT NULL,
    height INTEGER NOT NULL,
    manufacturer VARCHAR(100),
    description TEXT,

    CONSTRAINT fk_rack_room
        FOREIGN KEY (room_id)
        REFERENCES rooms(id)
);