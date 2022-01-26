package com.esm.epam.repository;

import com.esm.epam.entity.Tag;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * @param <T> describes type parameter
 */
public interface CRDDao<T> {
    /**
     * gets all values
     *
     * @return List with values
     */
    List<T> getAll();

    /**
     * gets filtered values
     *
     * @param params collection that contains {@link String} as
     *               key and {@link Object} as value
     * @return List with sorted values
     */
    default List<T> getFilteredList(MultiValueMap<String, Object> params) {
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
    T getById(Long id);

    /**
     * deletes element by id
     *
     * @param id is required element id
     * @return true when element was deleted
     */
    boolean deleteById(Long id);
}

