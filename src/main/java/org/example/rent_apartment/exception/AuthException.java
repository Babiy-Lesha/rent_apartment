package org.example.rent_apartment.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private int statusCode;

    public AuthException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
