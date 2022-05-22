package com.myproject.expo.expositions.service;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface UserService extends ChangeEmailPassService {
    User registerUser(User newUser) throws ServiceException;

    User getUserByEmailAndPass(String email, char[] pass) throws ServiceException;

    User updateBalance(User user, BigDecimal price) throws ServiceException;

    User buyExpo(User user, Exposition expo) throws ServiceException;

    List<User> getAllUsers(long page, long noOfRecords) throws ServiceException;

    boolean blockUnblockUser(String command, long userId) throws ServiceException;
}
