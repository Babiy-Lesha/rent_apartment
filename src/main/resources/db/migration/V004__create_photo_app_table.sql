CREATE TABLE IF NOT EXISTS photo_app (
     id INT8 PRIMARY KEY,
     date_add_photo timestamp(6),
     photo_name VARCHAR(255),
     encode_photo TEXT
);

CREATE SEQUENCE photo_app_sequence start 1 INCREMENT 1;

