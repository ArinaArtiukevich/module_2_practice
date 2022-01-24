package com.esm.epam.service;

import com.esm.epam.entity.Tag;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface CRDService<T> {
    List<T> getAll();

    default List<T> getFilteredList(MultiValueMap<String, Object> params) {
        return getAll();
    }

    Long add(T t);

    T getById(Long id);

    boolean deleteById(Long id);
}
