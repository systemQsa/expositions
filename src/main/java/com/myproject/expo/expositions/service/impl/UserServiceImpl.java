package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.UserDao;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.UserService;
import com.myproject.expo.expositions.service.encrypt.PassEncrypt;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new AbstractFactoryImpl().getDaoFactory().getUserDao();
    }

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserByEmailAndPass(String email, char[] pass) throws ServiceException {
        //todo encrypt password
        try {
            return getFoundedUser(email, pass);
        } catch (DaoException | ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private User getFoundedUser(String email, char[] pass) throws DaoException, ValidationException {
        User user = userDao.getUserByEmailAndPass(email, String.valueOf(pass));
        boolean decrypted = PassEncrypt.decryptPass(String.valueOf(user.getPassword()),"123", pass);
        if (!decrypted){
            throw new ValidationException("err.incorrect_password");
        }
        return user;
    }

    @Override
    public User registerUser(User newUser) throws ServiceException {
        try {
            return userDao.add(newUser);
        } catch (Exception e) {
            logger.warn(Constant.LogMsg.PROBLEM_REGISTER_USER_IN_SERVICE);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User updateBalance(User user, BigDecimal price) throws ServiceException {
        try {
            return userDao.updateBalance(user, price);
        } catch (DaoException e) {
            logger.warn("User " + user.getIdUser() + " cannot top up balance!");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User buyExpo(User user, Exposition expo) throws ServiceException {
        try {
            if(expo.getStatusId() == 2){
                throw new DaoException("err.expo_canceled");
            }
            return userDao.buyExpo(user, expo);
        } catch (DaoException e) {
            logger.warn("User cannot buy the expo " + expo.getIdExpo());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean changeEmail(String oldEmail, String newEmail) throws ServiceException {
        try {
            return userDao.changeEmail(oldEmail, newEmail);
        } catch (DaoException e) {
            logger.warn("User" + oldEmail + " cannot change the email");
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public boolean changePass(String email, char[] password) throws ServiceException {
        try {
            return userDao.changePass(email, String.valueOf(password));
        } catch (DaoException e) {
            logger.warn("User with email " + email + " can`t update the pass. UserServiceImpl class has failed");
            throw new ServiceException(e.getMessage());
        }
    }
}
