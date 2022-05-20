package com.myproject.expo.expositions.service;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;

public interface Searchable {
    List<Exposition> searchExpo(String searchBy, String searchedItem) throws ServiceException;

    List<Exposition> searchExpo(String searchBy, LocalDate localDate) throws ServiceException;
}
