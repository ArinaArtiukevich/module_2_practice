package com.esm.epam.validator.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.validator.ServiceValidator;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class ServiceCertificateValidatorImpl implements ServiceValidator<Certificate> {
    @Override
    public void validateEntity(Certificate certificate, Long id) throws ResourceNotFoundException {
        if (isNull(certificate)) {
            throw new ResourceNotFoundException("Requested certificate resource not found id = " + id);
        }
    }

    @Override
    public void validateEntityParameters(Certificate certificate) throws ServiceException {
        validate(certificate.getName());
        validate(certificate.getDescription());
        validate(certificate.getPrice());
        validate(certificate.getDuration());
        validate(certificate.getTags());
    }

    @Override
    public void validateList(List<Certificate> certificates) throws ResourceNotFoundException {
        if (isNull(certificates) || certificates.isEmpty()) {
            throw new ResourceNotFoundException("Certificate list is empty.");
        }
    }

    private void validate(String value) throws ServiceException {
        if (isNull(value)) {
            throw new ServiceException("String parameter is null. Enter value.");
        }
    }

    private void validate(Integer value) throws ServiceException {
        if (isNull(value) || value < 0) {
            throw new ServiceException("Long parameter is null. Enter value.");
        }
    }

    private void validate(List<Tag> tags) throws ServiceException {
        if (nonNull(tags)) {
            for (Tag tag : tags) {
                validate(tag.getName());
            }
        }
    }

}
