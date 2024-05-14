CREATE TABLE IF NOT EXISTS apartment_room_info
(
    id               INT8 PRIMARY KEY,
    rating_room      VARCHAR(255),
    price_per_day    INT4,
    count_room       INT4,
    have_shower_room BOOLEAN
);

CREATE SEQUENCE apartment_room_info_sequence start 2 INCREMENT 1;

INSERT INTO apartment_room_info (id, rating_room, price_per_day, count_room, have_shower_room)
VALUES (1, '3.5', 4, 3, true);