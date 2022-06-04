package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.exception.DaoException;

import java.sql.Connection;

public interface Updatable<T> {
    boolean update(T t, Connection connection) throws DaoException;
}
