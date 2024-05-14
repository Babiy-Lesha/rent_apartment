CREATE TABLE if not exists integration_info
(
    id        varchar PRIMARY KEY,
    url       varchar,
    key_value varchar
);

INSERT INTO integration_info (id, url, key_value)
VALUES ('GEO', 'https://api.opencagedata.com/geocode/v1/json?q=%s+%s&key=%s',
        'NGI2ZmM1ODZiMzEwNDcyN2FhOTNmODRkNTQ5ZjI0MTk=')

