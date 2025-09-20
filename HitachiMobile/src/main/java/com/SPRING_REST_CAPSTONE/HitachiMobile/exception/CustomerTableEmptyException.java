package com.SPRING_REST_CAPSTONE.HitachiMobile.exception;

public class CustomerTableEmptyException extends RuntimeException {
    public CustomerTableEmptyException(String message) {
        super(message);
    }
}
