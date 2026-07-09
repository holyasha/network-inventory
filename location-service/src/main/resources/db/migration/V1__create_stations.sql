CREATE TABLE stations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    line VARCHAR(100),
    address VARCHAR(255),
    description TEXT
);