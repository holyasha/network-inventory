CREATE TABLE ip_addresses (
    id BIGSERIAL PRIMARY KEY,
    subnet_id BIGINT NOT NULL,
    address VARCHAR(50) NOT NULL UNIQUE,
    allocated BOOLEAN NOT NULL DEFAULT FALSE,
    reserved BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_ip_subnet
        FOREIGN KEY (subnet_id)
        REFERENCES subnets(id)
);