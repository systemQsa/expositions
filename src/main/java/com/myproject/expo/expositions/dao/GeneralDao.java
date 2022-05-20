package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.exception.DaoException;

import java.util.List;

public interface GeneralDao<T> {
    T add(T t) throws DaoException;

    List<T> getAllRecords(long page, long noOfRecords, String querySortBy) throws DaoException;

    default long calcStartOffset(long currPage, long noOfRecords) {
        return currPage * noOfRecords - noOfRecords;
    }
}
