package com.esm.epam.repository;

public interface CRUDDao<T> extends CRDDao<T> {
    /**
     * updates element by id
     *
     * @param t is element with fields to be updated
     * @param idT of element to be updated
     */
    void update(T t, Long idT);
}
