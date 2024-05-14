CREATE TABLE IF NOT EXISTS address_info
(
    id               INT8 PRIMARY KEY,
    city_apartment   VARCHAR(255),
    name_apartment   VARCHAR(255),
    street_apartment VARCHAR(255),
    apartment_id     int8 REFERENCES apartment_room_info (id)
);

CREATE SEQUENCE address_info_sequence start 2 INCREMENT 1;

INSERT INTO address_info (id, city_apartment, name_apartment, street_apartment, apartment_id)
VALUES (1, 'Kosmos', '123App', '123App32', 1);