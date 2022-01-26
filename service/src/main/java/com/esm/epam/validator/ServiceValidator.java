package com.esm.epam.validator;

import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @param <T> describes type parameter
 */
public interface ServiceValidator<T> {
    /**
     * validates element
     *
     * @param t is parameter to be validated
     */
    void validateEntityParameters(T t) throws ServiceException;

    /**
     * validates element with id
     *
     * @param t  is parameter to be validated
     * @param id is id of parameter
     */
    void validateEntity(T t, Long id) throws ResourceNotFoundException;

    /**
     * validates list with elements
     *
     * @param t is list to be validated
     */
    void validateList(List<T> t) throws ResourceNotFoundException;

}
