package com.esm.epam.service;

import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface CRDService<T> {
    List<T> getAll() throws ResourceNotFoundException;

    default List<T> getFilteredList(MultiValueMap<String, Object> params) throws ServiceException, ResourceNotFoundException {
        return getAll();
    }

    Long add(T t) throws ServiceException;

    T getById(Long id) throws ResourceNotFoundException;

    void deleteById(Long id) throws ServiceException;
}
