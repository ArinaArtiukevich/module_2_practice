package com.esm.epam.controller;

import com.esm.epam.errorEntity.ErrorResponse;
import com.esm.epam.exception.ResourceNotFoundException;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;


@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
//todo localized
    // todo validation
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(1, exception.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

}