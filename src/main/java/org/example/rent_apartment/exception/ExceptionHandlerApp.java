package org.example.rent_apartment.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerApp {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> processingException (AuthException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
    }

    @ExceptionHandler(ApartmentException.class)
    public ResponseEntity<?> processingException (ApartmentException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
    }

}
