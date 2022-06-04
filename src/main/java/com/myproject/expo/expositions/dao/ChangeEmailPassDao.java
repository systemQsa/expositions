package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.exception.DaoException;

import java.sql.Connection;

public interface ChangeEmailPassDao {
    boolean changeEmail(String oldEmail, String newEmail, Connection connection) throws DaoException;

    boolean changePass(String email, String password,Connection connection) throws DaoException;
}
