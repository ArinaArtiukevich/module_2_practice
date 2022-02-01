package com.esm.epam.validator.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class ServiceCertificateValidatorImplTest {

    private ServiceCertificateValidatorImpl certificateValidator = new ServiceCertificateValidatorImpl();
    private Certificate certificate = Certificate.builder()
            .id(1L)
            .name("skiiing")
            .description("skiing in alps")
            .price(100)
            .duration(200)
            .build();

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
            certificateValidator.validateEntityParameters(
                    Certificate.builder()
                            .id(1L)
                            .name(null)
                            .description("skiing in alps")
                            .price(100)
                            .duration(200)
                            .build());
        });
    }

    @Test
    void validateEntityParameters_positive() throws ServiceException {
        certificateValidator.validateEntityParameters(certificate);
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