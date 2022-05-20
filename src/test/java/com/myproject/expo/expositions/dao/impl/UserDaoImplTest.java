package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.UserDao;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.impl.connection.DBManager;
import com.myproject.expo.expositions.exception.DaoException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class UserDaoImplTest {
    private UserDao userDao;
    private final User user = User.builder()
            .setName("name")
            .setSurname("surname")
            .setEmail("user23@gmail.com")
            .setPassword(new char[]{'1', '2', '3'})
            .setPhone("+234567823")
            .build();
    private final User updateUser = User.builder()
            .setIdUser(2)
            .setName("Jessica")
            .setSurname("Thompson")
            .setEmail("jess@gmail.com")
            .setPhone("+1234567888")
            .setBalance(new BigDecimal("0.0"))
            .build();


    @BeforeClass
    public static void init() {
        DBManager.getInstance().loadScript();
    }

    @Before
    public void beforeEach() {
        DBManager dbManager = DBManager.getInstance();
        userDao = new UserDaoImpl(dbManager);
    }

    @Test
    public void add() {
        assertDoesNotThrow(() -> userDao.add(user));
    }

    @Test
    public void getUserByEmailAndPass() throws DaoException {
        User res = userDao.getUserByEmailAndPass("peter@gmail.com", "1234");

        Assertions.assertEquals("+32403567667", res.getPhone());
    }

    @Test
    public void updateBalance() throws DaoException {
        User resUser = userDao.updateBalance(updateUser, new BigDecimal(100));
        Assertions.assertEquals(BigDecimal.valueOf(100.0),resUser.getBalance());
    }

    @Test
    public void changeEmail(){
      assertDoesNotThrow(() -> userDao.changeEmail("peter@gmail.com","peter1@gmail.com"));
    }

    @Test(expected = DaoException.class)
    public void changePass() throws DaoException {
        userDao.changePass("peter123@gmail.com","2345");
    }

}