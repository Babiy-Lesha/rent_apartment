ALTER SEQUENCE apartment_room_info_sequence RESTART WITH 8;

INSERT INTO apartment_room_info (id, rating_room, price_per_day, count_room, have_shower_room)
VALUES (2, '2.5', 7, 2, true),
       (3, '1.5', 6, 4, false),
       (4, '5.5', 9, 1, false),
       (5, '5.7', 10, 5, true),
       (6, '5.8', 2, 2, false),
       (7, '5.9', 1, 1, true);