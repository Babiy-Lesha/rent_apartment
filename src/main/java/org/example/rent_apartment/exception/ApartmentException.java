package org.example.rent_apartment.exception;

import lombok.Getter;

@Getter
public class ApartmentException extends RuntimeException {

    public int statusCode;

    public ApartmentException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
