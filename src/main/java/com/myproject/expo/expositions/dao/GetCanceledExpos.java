package com.myproject.expo.expositions.dao;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.DaoException;

import java.util.List;

public interface GetCanceledExpos {
    List<Exposition> getCanceledExposForUser(User user, int statusId, long page, long noOfRecords) throws DaoException;

}
