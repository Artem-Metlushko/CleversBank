package com.clever.bank.exception;

public class InitializationLoadException extends RuntimeException {

    public InitializationLoadException(String message) {
        super(message);
    }

    public InitializationLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}

