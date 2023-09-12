package com.clever.bank.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, T> {

    T save(T entity);
    T update(T entity);
    Optional<T> findById(K id);
    boolean deleteById(K id);
    List<T> findAll();

}
