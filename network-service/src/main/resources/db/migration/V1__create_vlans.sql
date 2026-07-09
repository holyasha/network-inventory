CREATE TABLE vlans (
    id BIGSERIAL PRIMARY KEY,
    number INTEGER NOT NULL UNIQUE,
    name VARCHAR(100),
    description TEXT
);