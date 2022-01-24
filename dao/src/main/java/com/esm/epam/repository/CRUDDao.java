package com.esm.epam.repository;

public interface CRUDDao<T> extends CRDDao<T>{
   void update(T t, Long idT);
}
