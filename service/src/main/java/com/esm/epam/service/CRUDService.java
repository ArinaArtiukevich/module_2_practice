package com.esm.epam.service;

public interface CRUDService<T> extends CRDService<T> {
    boolean update(T t, Long idT);

}
