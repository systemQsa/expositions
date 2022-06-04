package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface GeneralDao<T> {
    T add(T t, Connection connection) throws DaoException;

    List<T> getAllRecords(long page, long noOfRecords, String querySortBy,Connection connection) throws DaoException;

    default long calcStartOffset(long currPage, long noOfRecords) {
        return currPage * noOfRecords - noOfRecords;
    }
}
