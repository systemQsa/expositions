package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.UserDao;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.impl.connection.DBManager;
import com.myproject.expo.expositions.exception.DaoException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.List;

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
            .setBalance(new BigDecimal("500.0"))
            .build();
    private static final Exposition expo = Exposition.builder()
            .setExpositionID(2)
            .setTickets(45)
            .setExpoPrice(new BigDecimal("300.50"))
            .setExpoSoldTickets(15)
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
    public void getUserByEmailAndPass() throws DaoException {
        User res = userDao.getUserByEmailAndPass("art@gmail.com", "1234");
        Assertions.assertEquals("+56234567888", res.getPhone());
    }

    @Test
    public void add() {
        assertDoesNotThrow(() -> userDao.add(user));
    }

    @Test
    public void updateBalance() throws DaoException {
        User resUser = userDao.updateBalance(updateUser, new BigDecimal(100));
        Assertions.assertEquals(BigDecimal.valueOf(600.0), resUser.getBalance());
    }

    @Test
    public void changeEmail() {
        assertDoesNotThrow(() -> userDao.changeEmail("peter@gmail.com", "peter1@gmail.com"));
    }

    @Test(expected = DaoException.class)
    public void changePass() throws DaoException {
        userDao.changePass("peter123@gmail.com", "2345");
    }

    @Test
    public void buyExpo() {
        assertDoesNotThrow(() -> userDao.buyExpo(updateUser, expo));
    }

    @Test
    public void getAllRecords() throws DaoException {
        List<User> users = userDao.getAllRecords(1, 3, "query");
        assertEquals(3, users.size());
    }

    @Test
    public void blockUnblockUser() throws DaoException {
        Assertions.assertTrue(userDao.blockUnblockUser(3, 3));
    }

}