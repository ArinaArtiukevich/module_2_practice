package com.esm.epam.validator.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.service.impl.CertificateServiceImpl;
import com.esm.epam.validator.CertificateValidator;
import com.esm.epam.validator.ServiceValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.esm.epam.util.ParameterAttribute.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class ServiceCertificateValidatorImpl implements CertificateValidator {
    @Override
    public void validateEntity(Certificate certificate, Long id) {
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
    public void validateIntToBeUpdated(Integer value) throws ServiceException {
        if (nonNull(value)) {
            if (value <= 0) {
                throw new ServiceException("Invalid integer value");
            }
        }
    }

    @Override
    public void validateSortValues(MultiValueMap<String, Object> params) throws ServiceException {
        if (params.keySet().stream().anyMatch(key -> !SORT_KEYS.contains(key))) {
            throw new ServiceException("Invalid filter key.");
        }
    }

    @Override
    public void validateList(List<Certificate> certificates) {
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
