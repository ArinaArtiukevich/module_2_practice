package com.esm.epam.service;

import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * @param <T> describes type parameter
 */
public interface CRDService<T> {
    /**
     * gets all values
     *
     * @return List with values
     */

    List<T> getAll() throws ResourceNotFoundException;

    /**
     * gets filtered values
     *
     * @param params collection that contains {@link String} as
     *               key and {@link Object} as value
     * @return List with sorted values
     */
    default List<T> getFilteredList(MultiValueMap<String, Object> params) throws ResourceNotFoundException {
        return getAll();
    }

    /**
     * adds new element
     *
     * @param t the type of element to be added
     * @return id of added element
     */
    Long add(T t);

    /**
     * finds element by id
     *
     * @param id is required element id
     * @return required element
     */
    T getById(Long id) throws ResourceNotFoundException;

    /**
     * deletes element by id
     *
     * @param id is required element id
     */
    void deleteById(Long id) throws ServiceException;
}
