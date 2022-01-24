package com.esm.epam.repository;

import org.springframework.util.MultiValueMap;

public interface QueryBuilder<T> {
    String getUpdateQuery(T t, Long idT);

    String getFilteredList(MultiValueMap<String, Object> params);
}
