package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.exception.DaoException;

import java.time.LocalDate;
import java.util.List;

public interface SearchableDao {
    List<Exposition> searchExpo(String query, String searchedItem) throws DaoException;

    List<Exposition> searchExpo(String query, LocalDate localDate) throws DaoException;
}
