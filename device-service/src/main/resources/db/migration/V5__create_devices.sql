CREATE TABLE devices (
    id BIGSERIAL PRIMARY KEY,
    inventory_number VARCHAR(50) NOT NULL UNIQUE,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    device_model_id BIGINT NOT NULL,
    status_id BIGINT NOT NULL,
    purchase_date DATE,
    installation_date DATE,
    warranty_until DATE,
    comment TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_device_model
        FOREIGN KEY (device_model_id)
        REFERENCES device_models(id),

    CONSTRAINT fk_device_status
        FOREIGN KEY (status_id)
        REFERENCES device_statuses(id)
);

CREATE INDEX idx_device_inventory
ON devices(inventory_number);

CREATE INDEX idx_device_serial
ON devices(serial_number);

CREATE INDEX idx_device_model
ON devices(device_model_id);