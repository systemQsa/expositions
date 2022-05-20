package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.exception.DaoException;

public interface ChangeExpoStatusDao {
    boolean changeStatus(long expoId, int statusId) throws DaoException;
}
