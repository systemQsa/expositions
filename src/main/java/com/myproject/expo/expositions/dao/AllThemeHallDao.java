package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface AllThemeHallDao {
    List<Hall> allHalls(Connection connection) throws DaoException;

    List<Theme> allThemes(Connection connection) throws DaoException;
}
