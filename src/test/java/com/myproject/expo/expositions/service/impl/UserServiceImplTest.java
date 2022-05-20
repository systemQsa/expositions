package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.UserDao;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.impl.UserDaoImpl;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private static UserDao userDao;
    private static UserService userService;
    private static final User user = User.builder()
            .setName("name")
            .setEmail("user@gmail.com")
            .setPhone("+324566789795")
            .setPassword(new char[]{'1', '2', '3'})
            .setBalance(new BigDecimal("10"))
            .build();

    @BeforeClass
    public static void init() {
        userDao = mock(UserDaoImpl.class);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    public void registerUser() {
        assertDoesNotThrow(() -> when(userDao.add(user)).thenReturn(user));
        assertDoesNotThrow(() -> userService.registerUser(user));
    }

    @Test
    public void getUserByEmailAndPass() {
        assertDoesNotThrow(() -> when(userDao.getUserByEmailAndPass("user@gmail.com", "123")).thenReturn(user));
        assertDoesNotThrow(() -> userService.getUserByEmailAndPass(user.getEmail(), user.getPassword()));
    }

    @Test
    public void updateBalance() throws DaoException, ServiceException {
        when(userDao.updateBalance(user,new BigDecimal("10"))).thenReturn(user);
        Assertions.assertEquals(BigDecimal.valueOf(10),userService.updateBalance(user, new BigDecimal("10")).getBalance());
    }
}
