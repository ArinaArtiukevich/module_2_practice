package com.esm.epam.validator;


import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ResourceNotFoundException;

import java.util.List;

import static java.util.Objects.isNull;

public class Validator {
    public static void validateId(Long idT) {
        if (isNull(idT) || idT <= 0) {
            throw new ResourceNotFoundException("Invalid id = " + idT);
        }
    }
}
