package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity_idao.HallDao;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.generator.TestEntity;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HallDaoImplTest {
    private static HallDao hallDao;
    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resSet;

    @BeforeClass
    public static void init() {
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resSet = mock(ResultSet.class);
        hallDao = new HallDaoImpl(mock(ConnectionPool.class));
    }

    @Before
    public void before() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
    }

    @Test
    public void add() throws SQLException, DaoException {
        addHallInvokeMockMethods();
        Assertions.assertEquals(1, hallDao.add(TestEntity.HallTest.addHall, connection).getIdHall());
    }

    private void addHallInvokeMockMethods() throws SQLException {
        when(connection.prepareStatement(Query.HallSQL.ADD_NEW_HALL, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(resSet);
        when(resSet.next()).thenReturn(true);
        when(resSet.getLong(1)).thenReturn(1L);
    }

    @Test
    public void getAllRecords() throws DaoException, SQLException {
        getAllHallsMockMethods();
        Assertions.assertEquals(2, hallDao.getAllRecords(1, 3, anyString(), connection).size());
    }

    private void getAllHallsMockMethods() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resSet);
        when(resSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resSet.getInt(1)).thenReturn(1);
        when(resSet.getString(2)).thenReturn("someHallName");
    }

    @Test
    public void update() {
        assertDoesNotThrow(() -> hallDao.update(TestEntity.HallTest.updateHall, connection));
    }

    @Test
    public void remove() throws Exception {
        when(statement.executeQuery()).thenReturn(resSet);
        when(resSet.next()).thenReturn(false);

        Assertions.assertTrue(hallDao.remove(1, connection));

    }

}