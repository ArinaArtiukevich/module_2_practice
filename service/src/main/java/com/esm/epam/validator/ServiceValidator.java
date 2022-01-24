package com.esm.epam.validator;

import com.esm.epam.exception.ServiceException;
import org.springframework.stereotype.Component;

public interface ServiceValidator<T> {
    void validate(T t) throws ServiceException;

}
