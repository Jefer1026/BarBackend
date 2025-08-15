package com.jogdev.barbackend.bar.exception;

public class ApiErrorException extends RuntimeException {

    public ApiErrorException() {
    }

    public ApiErrorException(String message) {
        super(message);
    }

    public ApiErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}

