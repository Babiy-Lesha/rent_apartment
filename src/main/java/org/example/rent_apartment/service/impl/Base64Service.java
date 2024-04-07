package org.example.rent_apartment.service.impl;

import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Service {

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
            e.printStackTrace();
            return null;
        }
    }
}
