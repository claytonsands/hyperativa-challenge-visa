CREATE TABLE batch (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    batch_date DATE NOT NULL,
    batch_code CHAR(8) NOT NULL
);

CREATE TABLE card (
    id SERIAL PRIMARY KEY,
    batch_id INTEGER REFERENCES batch(id),
    card_encrypted BYTEA NOT NULL,
    card_masked VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    line_identifier CHAR(1),
    order_in_batch INTEGER NOT NULL
);
