package org.example.rent_apartment.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.example.rent_apartment.exception.response_status.CustomStatusResponse.BAD_REQUEST_INPUT_LOCATION;

public class Base64Service {

    private static final Logger log = LoggerFactory.getLogger(Base64Service.class);

    public static String decodeRentApp (String value) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(value);
        return new String(decode);
    }

    public static String encodeRentApp (String value) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    public static String encodeRentAppPhoto (MultipartFile value) {
        try {
            byte[] fileContent = value.getBytes();
            Base64.Encoder encoder = Base64.getEncoder();
            String encodedString = encoder.encodeToString(fileContent);
            return encodedString;
        } catch (Exception e) {
            log.error("Base64Service: encodeRentAppPhoto = " + e);
            e.printStackTrace();
            return null;
        }
    }
}
