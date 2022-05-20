package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.exception.DaoException;

public interface Updatable<T> {
    boolean update(T t) throws DaoException;
}
