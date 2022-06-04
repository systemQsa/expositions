package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.AllThemeHallDao;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.util.Constant;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllThemeHallDaoImplTest {
    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resSet;
    private static AllThemeHallDao themeHallDao;

    @Before
    public void before() {
        statement = mock(PreparedStatement.class);
        resSet = mock(ResultSet.class);
        connection = mock(Connection.class);
        themeHallDao = new AllThemeHallDaoImpl();
    }

    @Test
    public void allHalls() throws DaoException, SQLException {
        mocksForAllHallsMethod();
        Assertions.assertEquals(2,themeHallDao.allHalls(connection).size());
    }

    private void mocksForAllHallsMethod() throws SQLException {
        when(connection.prepareStatement(Query.HallSQL.GET_ALL_HALLS)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resSet);
        when(resSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resSet.getLong(1)).thenReturn(1L);
        when(resSet.getString(Constant.Column.NAME)).thenReturn("name");
        when(resSet.getLong(1)).thenReturn(2L);
        when(resSet.getString(Constant.Column.NAME)).thenReturn("name2");
    }

    @Test
    public void allThemes() throws DaoException, SQLException {
        mocksForAllThemesMethod();
        Assertions.assertEquals(1,themeHallDao.allThemes(connection).size());
    }

    private void mocksForAllThemesMethod() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resSet);
        when(resSet.next()).thenReturn(true).thenReturn(false);
        when(resSet.getLong(1)).thenReturn(1L);
        when(resSet.getString(Constant.Column.NAME)).thenReturn("themeName");
    }
}