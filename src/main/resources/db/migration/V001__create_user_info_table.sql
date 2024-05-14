CREATE TABLE IF NOT EXISTS user_info
(
    id                INT8 PRIMARY KEY,
    date_registration timestamp(6),
    login_user        VARCHAR(255),
    nick_name         VARCHAR(255),
    parent_city       VARCHAR(255),
    password_user     VARCHAR(255),
    token             VARCHAR(255)
);


CREATE SEQUENCE user_info_sequence start 2 INCREMENT 1;

INSERT INTO user_info (id, date_registration, login_user, nick_name, parent_city, password_user, token)
VALUES (1, '2024-03-24 01:03:49.455751', 'katyLogin', 'katy', 'Sochi', 'qwerty6',
        'N2Y5NzVjODMtYTQxNC00ZDJhLWI1MjUtYTNhZmM4NWU1NzhlfDIwMjQtMDMtMjVUMTM6MjQ6NDcuNTk2NDgyNDAw');