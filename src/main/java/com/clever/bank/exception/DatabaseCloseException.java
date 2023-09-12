package com.clever.bank.exception;

public class DatabaseCloseException extends RuntimeException {

    public DatabaseCloseException(String message) {
        super(message);
    }

    public DatabaseCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}

