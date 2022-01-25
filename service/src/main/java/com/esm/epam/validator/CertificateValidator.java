package com.esm.epam.validator;

import com.esm.epam.entity.Certificate;
import com.esm.epam.exception.ServiceException;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface CertificateValidator extends ServiceValidator<Certificate> {
    void validateIntToBeUpdated(Integer value) throws ServiceException;

    void validateSortValues(MultiValueMap<String, Object> params) throws ServiceException;
}
