package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.exception.DaoException;

import java.sql.Connection;

public interface ChangeExpoStatusDao {
    boolean changeStatus(long expoId, int statusId, Connection connection) throws DaoException;
}
