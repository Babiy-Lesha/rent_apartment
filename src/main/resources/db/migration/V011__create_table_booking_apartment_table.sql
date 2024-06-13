CREATE TABLE IF NOT EXISTS booking_apartment
(
    id               INT8 PRIMARY KEY,
    id_apartment     int8,
    name_apartment   VARCHAR(255),
    start_date_booking VARCHAR(255),
    end_date_booking VARCHAR(255),
    price_per_day int4
);

CREATE SEQUENCE booking_apartment_sequence start 1 INCREMENT 1;
