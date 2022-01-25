package com.esm.epam.validator;

import com.esm.epam.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ServiceValidator<T> {
    void validateEntityParameters(T t) throws ServiceException;
    void validateEntity(T t, Long id);
    void validateList(List<T> t);

}
