package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.exception.DaoException;

public interface ChangeEmailPassDao {
    boolean changeEmail(String oldEmail, String newEmail) throws DaoException;

    boolean changePass(String email, String password) throws DaoException;
}
