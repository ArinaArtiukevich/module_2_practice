package com.esm.epam.validator.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ServiceCertificateValidatorImplTest {

    private ServiceCertificateValidatorImpl certificateValidator = new ServiceCertificateValidatorImpl();
    private Certificate certificate = new Certificate(1L, "skiiing", "skiing in alps", 100, 200);

    @Test
    void testValidateEntity_resourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateValidator.validateEntity(null, 1L);
        });
    }

    @Test
    void testValidateEntity_positive() throws ResourceNotFoundException {
        certificateValidator.validateEntity(certificate, 1L);
    }

    @Test
    void validateEntityParameters_serviceException() {
        Assertions.assertThrows(ServiceException.class, () -> {
            certificateValidator.validateEntityParameters(new Certificate(1L, null, "skiing in alps", 100, 200));
        });
    }

    @Test
    void validateEntityParameters_positive() throws ServiceException {
        certificateValidator.validateEntityParameters(certificate);
    }

    @Test
    void validateIntToBeUpdated_serviceException() {
        Assertions.assertThrows(ServiceException.class, () -> {
            certificateValidator.validateIntToBeUpdated(0);
        });
    }

    @Test
    void validateList_resourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateValidator.validateList(new ArrayList<>());
        });
    }

    @Test
    void validateList() throws ResourceNotFoundException {
        certificateValidator.validateList(Arrays.asList(certificate));

    }
}