package com.esm.epam.validator.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.validator.ServiceValidator;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ServiceTagValidatorImpl implements ServiceValidator<Tag> {
    @Override
    public void validate(Tag tag) throws ServiceException {
        if (isNull(tag.getName())) {
            throw new ServiceException("String parameter is null. Enter value.");
        }
    }
}
