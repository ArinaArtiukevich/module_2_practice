package com.esm.epam.validator.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

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
            certificateValidator.validateEntity(Optional.empty(), 1L);
        });
    }

    @Test
    void testValidateEntity_positive() throws ResourceNotFoundException {
        certificateValidator.validateEntity(Optional.ofNullable(certificate), 1L);
    }

    @Test
    void validateListIsEmpty_resourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateValidator.validateListIsEmpty(new ArrayList<>());
        });
    }

    @Test
    void validateListIsNull_resourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            certificateValidator.validateListIsNull(Optional.empty());
        });
    }


    @Test
    void validateListIsEmpty_positive() throws ResourceNotFoundException {
        certificateValidator.validateListIsEmpty(Arrays.asList(certificate));

    }

    @Test
    void validateListIsNull_positive() throws ResourceNotFoundException {
        certificateValidator.validateListIsNull(Optional.of(Arrays.asList(certificate)));

    }
}