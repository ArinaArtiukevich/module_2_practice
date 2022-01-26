package com.esm.epam.validator;

import com.esm.epam.exception.ControllerException;

import static java.util.Objects.isNull;

public class Validator {
    public static void validateId(Long idT) throws ControllerException {
        if (isNull(idT) || idT <= 0) {
            throw new ControllerException("Invalid id = " + idT);
        }
    }
}
