package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity_idao.UserDao;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.impl.UserDaoImpl;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.generator.TestEntity;
import com.myproject.expo.expositions.service.entity_iservice.UserService;
import com.myproject.expo.expositions.service.encrypt.PassEncrypt;
import com.myproject.expo.expositions.util.Constant;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private static UserDao userDao;
    private static UserService userService;
    private static PassEncrypt passEncrypt;
    private static Connection connection;
    private static ConnectManager manager;
    private static final User user = TestEntity.UserTest.getUser;

    @BeforeClass
    public static void init() {
        userDao = mock(UserDaoImpl.class);
        passEncrypt = mock(PassEncrypt.class);
        manager = mock(ConnectionPool.class);
        userService = new UserServiceImpl(userDao, passEncrypt, manager);
    }

    @Before
    public void before() {
        connection = mock(Connection.class);
        when(manager.getConnection()).thenReturn(connection);
    }

    @Test
    public void registerUser() {
        assertDoesNotThrow(() -> when(userDao.add(user, connection)).thenReturn(TestEntity.UserTest.getUser));
        assertDoesNotThrow(() -> userService.registerUser(user));
    }

    @Test
    public void getUserByEmailAndPass() throws ValidationException {
        assertDoesNotThrow(() ->
                when(userDao.getUserByEmailAndPass("user@gmail.com", "123", connection)).thenReturn(user));
        when(passEncrypt.decryptPass(String.valueOf(user.getPassword()), Constant.KEY, new char[]{'1', '2', '3'})).thenReturn(true);
        assertDoesNotThrow(() -> userService.getUserByEmailAndPass(user.getEmail(), user.getPassword()));
    }

    @Test
    public void updateBalance() throws DaoException, ServiceException {
        when(userDao.updateBalance(user, new BigDecimal("500"), connection)).thenReturn(user);
        Assertions.assertEquals(BigDecimal.valueOf(500.0), userService.updateBalance(user, new BigDecimal("500")).getBalance());
    }

    @Test
    public void buyExpo() throws DaoException {
        User user = User.builder().setIdUser(14).build();
        Exposition expo = Exposition.builder().setExpositionID(23).setStatusId(1).build();
        when(userDao.buyExpo(user, expo, connection)).thenReturn(user);

        assertDoesNotThrow(() -> userService.buyExpo(user, expo));
    }

    @Test
    public void changeEmail() {
        assertDoesNotThrow(() -> when(userDao.changeEmail("old@gmail.com", "new@gmail.com",
                connection)).thenReturn(true));
        assertDoesNotThrow(() -> userService.changeEmail("old@gmail.com", "new@gmail.com"));
    }

    @Test
    public void changePass() {
        assertDoesNotThrow(() -> when(userDao.changePass("email@gmail.com", "123", connection))).thenReturn(true);
        assertDoesNotThrow(() -> userService.changePass("email@gmail.com", new char[]{'1', '2', '3'}));
    }

    @Test
    public void getAllUsers() throws DaoException, ServiceException {
        when(userDao.getAllRecords(1, 10, Query.UserSQL.GET_ALL_USERS, connection))
                .thenReturn(Collections.singletonList(user));
        List<User> allUsers = userService.getAllUsers(1, 10);
        Assertions.assertEquals(1, allUsers.size());

    }

    @Test
    public void blockUnblockUser() throws DaoException {
        when(userDao.blockUnblockUser(3, 1, connection)).thenReturn(true);
        assertDoesNotThrow(() -> userService.blockUnblockUser("blocked", 1));
    }
}
