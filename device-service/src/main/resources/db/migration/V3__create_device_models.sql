CREATE TABLE device_models (
    id BIGSERIAL PRIMARY KEY,
    manufacturer_id BIGINT NOT NULL,
    device_type_id BIGINT NOT NULL,
    model VARCHAR(100) NOT NULL,
    description TEXT,
    ports_count INTEGER,
    management_type VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_model_manufacturer
        FOREIGN KEY (manufacturer_id)
        REFERENCES manufacturers(id),

    CONSTRAINT fk_model_type
        FOREIGN KEY (device_type_id)
        REFERENCES device_types(id)
);