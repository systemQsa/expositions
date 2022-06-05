package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity_idao.ThemeDao;
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

public class ThemeDaoImplTest {
    private static ThemeDao themeDao;
    private Connection connection;
    private static PreparedStatement statement;
    private static PreparedStatement checkIfThemePresentInExpo;
    private static ResultSet resSet;

    @BeforeClass
    public static void init() {
        ConnectManager connectManager = mock(ConnectionPool.class);
        statement = mock(PreparedStatement.class);
        checkIfThemePresentInExpo = mock(PreparedStatement.class);
        resSet = mock(ResultSet.class);
        themeDao = new ThemeDaoImpl();
    }

    @Before
    public void before() throws SQLException {
        connection = mock(Connection.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
    }

    @Test
    public void add() throws SQLException {
        addThemeMockMethodsCall();
        assertDoesNotThrow(() -> themeDao.add(TestEntity.ThemeTest.addTheme, connection));

    }

    private void addThemeMockMethodsCall() throws SQLException {
        when(connection.prepareStatement(Query.ThemeSQL.ADD_NEW_THEME,
                Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resSet);
        when(resSet.next()).thenReturn(true);
        when(resSet.getLong(1)).thenReturn(1L);
    }

    @Test
    public void getAllRecords() throws DaoException, SQLException {
        getAllThemesMockMethodsCall();
        Assertions.assertEquals(2, themeDao.getAllRecords(1, 3, anyString(), connection).size());

    }

    private void getAllThemesMockMethodsCall() throws SQLException {
        when(statement.executeQuery()).thenReturn(resSet);
        when(resSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resSet.getLong(1)).thenReturn(1L);
        when(resSet.getString(2)).thenReturn("someThemeName");
    }

    @Test
    public void update() throws DaoException {
        Assertions.assertTrue(themeDao.update(TestEntity.ThemeTest.themeJobbing, connection));
    }

    @Test
    public void remove() throws Exception {
        removeThemeMockMethods();
        Assertions.assertTrue(themeDao.remove(23, connection));
    }

    private void removeThemeMockMethods() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(checkIfThemePresentInExpo);
        when(checkIfThemePresentInExpo.executeQuery()).thenReturn(resSet);
        when(resSet.next()).thenReturn(false);
    }
}