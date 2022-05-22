package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.UserDao;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.impl.UserDaoImpl;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.service.UserService;
import com.myproject.expo.expositions.service.encrypt.PassEncrypt;
import com.myproject.expo.expositions.util.Constant;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private static UserDao userDao;
    private static UserService userService;
    private static PassEncrypt passEncrypt;
    private static final User user = User.builder()
            .setName("name")
            .setEmail("user@gmail.com")
            .setPhone("+324566789795")
            .setPassword(new char[]{'1', '2', '3'})
            .setBalance(new BigDecimal("500.0"))
            .build();

    @BeforeClass
    public static void init() {
        userDao = mock(UserDaoImpl.class);
        passEncrypt = mock(PassEncrypt.class);
        userService = new UserServiceImpl(userDao, passEncrypt);
    }

    @Test
    public void registerUser() {
        assertDoesNotThrow(() -> when(userDao.add(user)).thenReturn(user));
        assertDoesNotThrow(() -> userService.registerUser(user));
    }

    @Test
    public void getUserByEmailAndPass() throws ValidationException {
        assertDoesNotThrow(() -> when(userDao.getUserByEmailAndPass("user@gmail.com", "123")).thenReturn(user));
        when(passEncrypt.decryptPass(String.valueOf(user.getPassword()), Constant.KEY, new char[]{'1', '2', '3'})).thenReturn(true);
        assertDoesNotThrow(() -> userService.getUserByEmailAndPass(user.getEmail(), user.getPassword()));
    }

    @Test
    public void updateBalance() throws DaoException, ServiceException {
        when(userDao.updateBalance(user, new BigDecimal("500"))).thenReturn(user);
        Assertions.assertEquals(BigDecimal.valueOf(500.0), userService.updateBalance(user, new BigDecimal("500")).getBalance());
    }

    @Test
    public void buyExpo() throws DaoException {
        User user = User.builder().setIdUser(14).build();
        Exposition expo = Exposition.builder().setExpositionID(23).setStatusId(1).build();
        when(userDao.buyExpo(user, expo)).thenReturn(user);

        assertDoesNotThrow(() -> userService.buyExpo(user, expo));
    }

    @Test
    public void changeEmail() {
        assertDoesNotThrow(() -> when(userDao.changeEmail("old@gmail.com", "new@gmail.com")).thenReturn(true));
        assertDoesNotThrow(() -> userService.changeEmail("old@gmail.com", "new@gmail.com"));
    }

    @Test
    public void changePass() {
        assertDoesNotThrow(() -> when(userDao.changePass("email@gmail.com", "123"))).thenReturn(true);
        assertDoesNotThrow(() -> userService.changePass("email@gmail.com", new char[]{'1', '2', '3'}));
    }

    @Test
    public void getAllUsers() throws DaoException, ServiceException {
        when(userDao.getAllRecords(1, 10, Query.UserSQL.GET_ALL_USERS)).thenReturn(Collections.singletonList(user));
        List<User> allUsers = userService.getAllUsers(1, 10);
        Assertions.assertEquals(1, allUsers.size());

    }

    @Test
    public void blockUnblockUser() throws DaoException {
        when(userDao.blockUnblockUser(3, 1)).thenReturn(true);
        assertDoesNotThrow(() -> userService.blockUnblockUser("blocked", 1));
    }
}
