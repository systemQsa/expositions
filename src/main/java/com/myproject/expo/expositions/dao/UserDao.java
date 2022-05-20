package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.DaoException;

import java.math.BigDecimal;

public interface UserDao extends GeneralDao<User>, ChangeEmailPassDao {
    User getUserByEmailAndPass(String email, String pass) throws DaoException;

    User updateBalance(User user, BigDecimal price) throws DaoException;

    User buyExpo(User user, Exposition expo) throws DaoException;

}
