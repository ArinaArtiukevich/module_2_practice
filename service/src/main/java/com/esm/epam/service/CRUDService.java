package com.esm.epam.service;

import com.esm.epam.exception.ServiceException;

public interface CRUDService<T> extends CRDService<T> {
    boolean update(T t, Long idT) throws ServiceException;

}
