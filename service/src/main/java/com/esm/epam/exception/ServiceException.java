package com.esm.epam.exception;


public class ServiceException extends Exception {
    private String message;

    public ServiceException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
