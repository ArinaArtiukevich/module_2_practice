package com.esm.epam.validator;

import com.esm.epam.entity.Certificate;
import com.esm.epam.exception.ServiceException;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface CertificateValidator extends ServiceValidator<Certificate> {
    /**
     * validates Integer
     *
     * @param value is parameter to be validated
     */
    void validateIntToBeUpdated(Integer value) throws ServiceException;

    /**
     * validates map with sorts values
     *
     * @param params collection that contains {@link String} as key and {@link Object} as value
     */
    void validateSortValues(MultiValueMap<String, Object> params) throws ServiceException;
}
