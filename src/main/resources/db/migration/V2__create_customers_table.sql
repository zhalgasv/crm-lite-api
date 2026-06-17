CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    phone VARCHAR(50),
    company VARCHAR(150),
    status VARCHAR(50) NOT NULL,
    owner_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_customers_owner
        FOREIGN KEY (owner_id)
        REFERENCES users(id)
)