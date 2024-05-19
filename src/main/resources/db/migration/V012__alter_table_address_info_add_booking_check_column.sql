ALTER TABLE address_info ADD COLUMN booking_check BOOLEAN DEFAULT FALSE;

UPDATE address_info SET booking_check = FALSE;