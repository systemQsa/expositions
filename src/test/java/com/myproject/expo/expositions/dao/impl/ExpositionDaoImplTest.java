package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity_idao.ExpositionDao;
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
import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpositionDaoImplTest {
    private static Connection connection;
    private static ExpositionDao expoDao;
    private static PreparedStatement statement;
    private static PreparedStatement deleteRefs;
    private static PreparedStatement insertNewRefs;
    private static ResultSet resSet;


    @BeforeClass
    public static void init() {
        ConnectManager connectManager = mock(ConnectionPool.class);
        statement = mock(PreparedStatement.class);
        deleteRefs = mock(PreparedStatement.class);
        insertNewRefs = mock(PreparedStatement.class);
        expoDao = new ExpositionDaoImpl(connectManager);
        resSet = mock(ResultSet.class);
    }

    @Before
    public void before() {
        connection = mock(Connection.class);
    }

    @Test
    public void getAllRecords() throws DaoException, SQLException {
        getAllExposMockMethodsCall();
        Assertions.assertEquals(1, expoDao.getAllRecords(1, 3, anyString(), connection).size());
    }

    private void getAllExposMockMethodsCall() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resSet);
        when(resSet.next()).thenReturn(true).thenReturn(false);
        buildExposMock();
    }

    private void buildExposMock() throws SQLException {
        when(resSet.getLong(1)).thenReturn(1L);
        when(resSet.getString(Constant.Column.NAME)).thenReturn("anyString");
        when(resSet.getDate(Constant.Column.EXPO_DATE)).thenReturn(Date.valueOf(LocalDate.now()));
        when(resSet.getTime(Constant.Column.EXPO_TIME)).thenReturn(Time.valueOf(LocalTime.now()));
        when(resSet.getBigDecimal(Constant.Column.PRICE)).thenReturn(BigDecimal.valueOf(1234));
        when(resSet.getLong(Constant.Column.SOLD)).thenReturn(23L);
        buildThemeMock();
        when(resSet.getLong(Constant.Column.TICKETS)).thenReturn(34L);
        when(resSet.getInt(Constant.Column.STATUS_ID)).thenReturn(1);
        buildHallsMock();
    }

    private void buildThemeMock() throws SQLException {
        when(resSet.getLong(Constant.Column.ID_THEME_REF)).thenReturn(7L);
        when(resSet.getString(Constant.Column.T_NAME)).thenReturn("ThemeName");
    }

    private void buildHallsMock() throws SQLException {
        when(resSet.getString(Constant.Column.IDS)).thenReturn("13, 15");
        when(resSet.getString(Constant.Column.HALLS)).thenReturn("AD12, BB4");
    }

    @Test
    public void update() throws SQLException, DaoException {
        updateExpositionMockMethods();
        Assertions.assertTrue(expoDao.update(TestEntity.Expo.expo, connection));
    }

    private void updateExpositionMockMethods() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(connection.prepareStatement(anyString())).thenReturn(deleteRefs);
        when(connection.prepareStatement(anyString())).thenReturn(insertNewRefs);
        when(statement.executeUpdate()).thenReturn(1);
        when(deleteRefs.executeUpdate()).thenReturn(1);
        when(insertNewRefs.executeUpdate()).thenReturn(1);
    }

    @Test
    public void add() throws SQLException {
        addExpoMockMethods();
        Assertions.assertDoesNotThrow(() -> expoDao.add(TestEntity.Expo.expoAdd, connection));
    }

    private void addExpoMockMethods() throws SQLException {
        when(connection.prepareStatement(Query.ExpoSQL.ADD_NEW_EXPO, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(connection.prepareStatement(anyString())).thenReturn(deleteRefs);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(resSet);
        when(resSet.getLong(1)).thenReturn(34L);
        when(deleteRefs.executeUpdate()).thenReturn(1);
    }

    @Test
    public void changeStatus() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        Assertions.assertTrue(expoDao.changeStatus(12, 3, connection));
    }

    @Test
    public void searchExpoByTheme() throws DaoException, SQLException {
        searchExposMockMethod();
        Assertions.assertEquals(1, expoDao.searchExpo(anyString(), "searchedItem", connection).size());
    }

    @Test
    public void searchExpoByDate() throws DaoException, SQLException {
        searchExposMockMethod();
        Assertions.assertEquals(1, expoDao.searchExpo(Query.ExpoSQL.SEARCH_BY_DATE,
                LocalDate.of(2022, 5, 13), connection).size());

    }

    private void searchExposMockMethod() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resSet);
        when(resSet.next()).thenReturn(true).thenReturn(false);
        getAllExposMockMethodsCall();
    }

}