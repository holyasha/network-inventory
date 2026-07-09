CREATE TABLE rooms (
    id BIGSERIAL PRIMARY KEY,
    station_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    floor INTEGER,
    description TEXT,

    CONSTRAINT fk_room_station
        FOREIGN KEY (station_id)
        REFERENCES stations(id)
);