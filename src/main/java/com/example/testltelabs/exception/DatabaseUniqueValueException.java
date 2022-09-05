package com.example.testktelabs.exception;

public class DatabaseUniqueValueException extends RuntimeException {
    public DatabaseUniqueValueException(String message) {
        super(message);
    }
}
