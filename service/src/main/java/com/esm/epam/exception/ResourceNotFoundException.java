package com.esm.epam.exception;

import java.util.ResourceBundle;

public class ResourceNotFoundException extends Exception {
    private String message;

    public ResourceNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}