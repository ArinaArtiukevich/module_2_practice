package com.esm.epam.repository;

public interface CRUDDao<T> extends CRDDao<T> {
    /**
     * updates element by id
     *
     * @param t is element with fields to be updated
     * @param idT of element to be updated
     */
    T update(T t, Long idT);
}
