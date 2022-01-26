package com.esm.epam.exception;

public class ControllerException extends Exception {
    private String message;

    public ControllerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
