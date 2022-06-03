package com.myproject.expo.expositions.dao.entity_idao;

import com.myproject.expo.expositions.dao.ChangeEmailPassDao;
import com.myproject.expo.expositions.dao.GeneralDao;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.Status;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.DaoException;

import java.math.BigDecimal;
import java.util.Arrays;

public interface UserDao extends GeneralDao<User>, ChangeEmailPassDao {
    User getUserByEmailAndPass(String email, String pass) throws DaoException;

    User updateBalance(User user, BigDecimal price) throws DaoException;

    User buyExpo(User user, Exposition expo) throws DaoException;

    boolean blockUnblockUser(int newStatus,long userId) throws DaoException;

       default String defineStatus(int statusId) {
        return Arrays.stream(Status.values())
                .filter(status -> status.getStatusId() == statusId)
                .map(Status::getName)
                .findFirst()
                .orElse(null);
    }

}
