package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity_idao.UserDao;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.generator.TestEntity;
import com.myproject.expo.expositions.util.Constant;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class UserDaoImplTest {
    private static UserDao userDao;
    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resSet;

    @BeforeClass
    public static void init() {
        connection = mock(Connection.class);
        ConnectManager connectManager = mock(ConnectionPool.class);
        statement = mock(PreparedStatement.class);
        resSet = mock(ResultSet.class);
        userDao = new UserDaoImpl(connectManager);
    }

    @Before
    public void before() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
    }

    @Test
    public void add() throws SQLException {
        mocksAddUser();
        assertDoesNotThrow(() -> userDao.add(TestEntity.UserTest.user, connection));
    }

    private void mocksAddUser() throws SQLException {
        when(connection.prepareStatement(Query.UserSQL.REGISTER_USER,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(resSet);
        when(resSet.next()).thenReturn(true);
        when(resSet.getLong(1)).thenReturn(1L);
    }

    @Test
    public void getAllRecords() throws DaoException, SQLException {
        mockWhenGetAllUsers();
        Assertions.assertEquals(1, userDao.getAllRecords(1,
                5, Query.UserSQL.GET_ALL_USERS, connection).size());
    }

    private void mockWhenGetAllUsers() throws SQLException {
        when(statement.executeQuery()).thenReturn(resSet);
        when(resSet.next()).thenReturn(true).thenReturn(false);
        when(resSet.getLong(1)).thenReturn(1L);
        mockResultSetUsers();
    }

    private void mockResultSetUsers() throws SQLException {
        when(resSet.getString(Constant.Column.NAME)).thenReturn("Harry");
        when(resSet.getString(Constant.Column.SURNAME)).thenReturn("Potter");
        when(resSet.getString(Constant.Column.EMAIL)).thenReturn("some@gmail.com");
        when(resSet.getString(Constant.Column.PHONE)).thenReturn("+380903456123");
        when(resSet.getInt(Constant.Column.ROLE_ID)).thenReturn(2);
        when(resSet.getInt(Constant.Column.STATUS_ID)).thenReturn(1);
    }

    @Test(expected = DaoException.class)
    public void getUserByEmailAndPass() throws DaoException {
        try {
            when(statement.executeQuery()).thenReturn(resSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        userDao.getUserByEmailAndPass("some@gmail.com", "1234", connection);
    }

    @Test
    public void updateBalance() throws DaoException {
        Assertions.assertEquals(BigDecimal.valueOf(550.0), userDao.updateBalance(TestEntity.UserTest.updateUser,
                BigDecimal.valueOf(50.0), connection).getBalance());
    }

    @Test
    public void buyExpo() throws DaoException {
        Assertions.assertEquals(String.format("%.2f", 200.),
                String.valueOf(userDao.buyExpo(TestEntity.UserTest.user, TestEntity.UserTest.buyExpo, connection).getBalance()));
    }

    @Test
    public void changePass() throws DaoException {
        Assertions.assertTrue(userDao.changePass("peter123@gmail.com", "2345", connection));
    }

    @Test
    public void changeEmail() throws DaoException {
        Assertions.assertTrue(userDao.changeEmail("peter@gmail.com", "Potter@gmail.com", connection));
    }

    @Test
    public void blockUnblockUser() throws DaoException {
        Assertions.assertTrue(userDao.blockUnblockUser(3, 3, connection));
    }

}