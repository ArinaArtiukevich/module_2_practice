package com.esm.epam.validator.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.validator.ServiceValidator;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Component
public class ServiceTagValidatorImpl implements ServiceValidator<Tag> {

    @Override
    public void validateEntityParameters(Tag tag) throws ServiceException {
        if (isNull(tag.getName())) {
            throw new ServiceException("String parameter is null. Enter value.");
        }
    }

    @Override
    public void validateEntity(Tag tag, Long id) throws ResourceNotFoundException {
        if (isNull(tag)) {
            throw new ResourceNotFoundException("Requested tag resource not found id = " + id);
        }
    }

    @Override
    public void validateList(List<Tag> tags) throws ResourceNotFoundException {
        if (isNull(tags) || tags.isEmpty()) {
            throw new ResourceNotFoundException("Tag list is empty.");
        }
    }
}
