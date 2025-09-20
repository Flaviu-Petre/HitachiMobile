package com.SPRING_REST_CAPSTONE.HitachiMobile.exception;

public class CustomerDoesNotExistException extends RuntimeException {
    public CustomerDoesNotExistException(String message) {
        super(message);
    }
}
