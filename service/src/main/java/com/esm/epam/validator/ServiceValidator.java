package com.esm.epam.validator;

import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ServiceValidator<T> {
    void validateEntityParameters(T t) throws ServiceException;
    void validateEntity(T t, Long id) throws ResourceNotFoundException;
    void validateList(List<T> t) throws ResourceNotFoundException;

}
