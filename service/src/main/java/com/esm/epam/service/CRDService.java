package com.esm.epam.service;

import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ServiceException;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface CRDService<T> {
    List<T> getAll();

    default List<T> getFilteredList(MultiValueMap<String, Object> params) throws ServiceException {
        return getAll();
    }

    Long add(T t) throws ServiceException;

    T getById(Long id);

    void deleteById(Long id) throws ServiceException;
}
