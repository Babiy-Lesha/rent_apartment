ALTER SEQUENCE address_info_sequence RESTART WITH 8;

INSERT INTO address_info (id, city_apartment, name_apartment, street_apartment, apartment_id)
VALUES (2, 'Yekaterinburg', '123App', '123App32', 2),
       (3, 'Yekaterinburg', '123App', '123App32', 3),
       (4, 'Yekaterinburg', '123App', '123App32', 4),
       (5, 'Kosmos', 'Chelyabinsk', '123App32', 5),
       (6, 'Kosmos', 'Chelyabinsk', '123App32', 6),
       (7, 'Kosmos', 'Korkino', '123App32', 7);



