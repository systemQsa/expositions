package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.command.UtilMethods;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity_idao.UserDao;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.encrypt.PassEncrypt;
import com.myproject.expo.expositions.service.entity_iservice.UserService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService, UtilMethods {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final PassEncrypt passEncrypt;
     private final ConnectManager manager;
    public UserServiceImpl() {
        this.userDao = new AbstractFactoryImpl().getDaoFactory().getUserDao();
        passEncrypt = new PassEncrypt();
        manager = ConnectionPool.getInstance();
    }

    public UserServiceImpl(UserDao userDao, PassEncrypt passEncrypt,ConnectManager manager) {
        this.userDao = userDao;
        this.passEncrypt = passEncrypt;
        this.manager = manager;
    }

    @Override
    public User getUserByEmailAndPass(String email, char[] pass) throws ServiceException {
        try {
            return getFoundedUser(email, pass);
        } catch (DaoException | ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private User getFoundedUser(String email, char[] pass) throws DaoException, ValidationException {
        User user = userDao.getUserByEmailAndPass(email, String.valueOf(pass), manager.getConnection());
        boolean decrypted = passEncrypt.decryptPass(String.valueOf(user.getPassword()), Constant.KEY, pass);
        if (!decrypted) {
            throw new ValidationException(Constant.ErrMsg.INCORRECT_PASSWORD);
        }
        return user;
    }

    @Override
    public User registerUser(User newUser) throws ServiceException {
        try {
            return userDao.add(newUser, manager.getConnection());
        } catch (Exception e) {
            logger.warn(Constant.LogMsg.PROBLEM_REGISTER_USER_IN_SERVICE);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User updateBalance(User user, BigDecimal price) throws ServiceException {
        try {
            return userDao.updateBalance(user, price, manager.getConnection());
        } catch (DaoException e) {
            logger.warn("User " + user.getIdUser() + " cannot top up balance!");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User buyExpo(User user, Exposition expo) throws ServiceException {
        try {
            checkExpoAlreadyCanceled(expo);
            return userDao.buyExpo(user, expo, manager.getConnection());
        } catch (DaoException e) {
            logger.warn("User cannot buy the expo " + expo.getIdExpo());
            throw new ServiceException(e.getMessage());
        }
    }

    private void checkExpoAlreadyCanceled(Exposition expo) throws DaoException {
        if (expo.getStatusId() == 2) {
            throw new DaoException(Constant.ErrMsg.CANCELED_EXPO);
        }
    }

    @Override
    public boolean changeEmail(String oldEmail, String newEmail) throws ServiceException {
        try {
            return userDao.changeEmail(oldEmail, newEmail, manager.getConnection());
        } catch (DaoException e) {
            logger.warn("User" + oldEmail + " cannot change the email");
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public boolean changePass(String email, char[] password) throws ServiceException {
        try {
            return userDao.changePass(email, String.valueOf(password), manager.getConnection());
        } catch (DaoException e) {
            logger.warn("User with email " + email + " can`t update the pass. UserServiceImpl class has failed");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers(long page, long noOfRecords) throws ServiceException {
        try {
            return Optional.ofNullable(userDao.getAllRecords(page, noOfRecords, Query.UserSQL.GET_ALL_USERS, manager.getConnection()))
                    .filter(list -> list.size() > 0)
                    .orElseThrow(() -> new DaoException(Constant.ErrMsg.GET_ALL_USERS));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean blockUnblockUser(String command, long userId) throws ServiceException {
        int statusId = getStatusId(command);
        try {
            return userDao.blockUnblockUser(statusId,userId, manager.getConnection());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
