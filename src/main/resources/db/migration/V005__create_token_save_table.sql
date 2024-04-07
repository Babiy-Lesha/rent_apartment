CREATE TABLE IF NOT EXISTS token_save (
    id INT8 PRIMARY KEY,
    token_decode VARCHAR(255),
    token_encode VARCHAR(255)
);

CREATE SEQUENCE token_save_sequence start 1 INCREMENT 1;