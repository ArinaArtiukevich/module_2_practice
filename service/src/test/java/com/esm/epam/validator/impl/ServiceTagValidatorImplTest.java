package com.esm.epam.validator.impl;

import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTagValidatorImplTest {

    private ServiceTagValidatorImpl tagValidator = new ServiceTagValidatorImpl();
    private Tag tag =  new Tag(1L, "tag_winter");

    @Test
    void validateEntityParameters_serviceException() {
        Assertions.assertThrows(ServiceException.class, () -> {
            tagValidator.validateEntityParameters(new Tag(1L, null));
        });
    }

    @Test
    void validateEntityParameters_positive() throws ServiceException {
        tagValidator.validateEntityParameters(tag);
    }

    @Test
    void validateEntity_resourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            tagValidator.validateEntity(null, 1L);
        });
    }

    @Test
    void validateEntity_positive() throws ResourceNotFoundException {
        tagValidator.validateEntity(tag, 1L);
    }

    @Test
    void validateList_resourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            tagValidator.validateList(new ArrayList<>());
        });
    }

    @Test
    void validateList_positive() throws ResourceNotFoundException {
        tagValidator.validateList(Arrays.asList(tag));
    }

}