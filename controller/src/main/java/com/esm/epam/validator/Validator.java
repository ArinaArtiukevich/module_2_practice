package com.esm.epam.validator;


import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ResourceNotFoundException;

import java.util.List;

import static java.util.Objects.isNull;

public class Validator {
    public static void validateTagList(List<Tag> tags) {
        if (isNull(tags) || tags.isEmpty()) {
            throw new ResourceNotFoundException("Tag list is empty.");
        }
    }

    public static void validateCertificateList(List<Certificate> certificates) {
        if (isNull(certificates) || certificates.isEmpty()) {
            throw new ResourceNotFoundException("Certificate list is empty.");
        }
    }

    public static void validateEntity(Object t, Long idT) {
        if (isNull(t)) {
            throw new ResourceNotFoundException("Requested resource not found id = " + idT);
        }
    }
}
