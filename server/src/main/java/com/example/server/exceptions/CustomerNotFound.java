package com.example.server.exceptions;

public class CustomerNotFound extends RuntimeException {

    public CustomerNotFound(String message) {
        super(message);
    }
}
