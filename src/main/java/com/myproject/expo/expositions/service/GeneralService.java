package com.myproject.expo.expositions.service;

import com.myproject.expo.expositions.exception.ServiceException;

import java.util.List;

public interface GeneralService<T> {
    List<T> getAllRecords(long page, long noOfPages, String sortBy) throws ServiceException;

    T add(T t) throws ServiceException;

    boolean update(T type) throws ServiceException;
}
