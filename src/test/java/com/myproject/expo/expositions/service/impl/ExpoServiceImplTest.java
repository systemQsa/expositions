package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.entity_idao.ExpositionDao;
import com.myproject.expo.expositions.dao.impl.ExpositionDaoImpl;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.generator.TestEntity;
import com.myproject.expo.expositions.service.entity_iservice.ExpositionService;
import com.myproject.expo.expositions.validation.Validate;
import com.myproject.expo.expositions.validation.ValidateInput;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpoServiceImplTest {
    private static ExpositionDao expoDao;
    private static ExpositionService<Exposition> expoService;
    private static ConnectManager manager;
    private static Connection connection;

    @BeforeClass
    public static void init() {
        expoDao = mock(ExpositionDaoImpl.class);
        Validate validate = mock(ValidateInput.class);
        manager = mock(ConnectionPool.class);
        expoService = new ExpoServiceImpl(expoDao, validate, manager);
    }

    @Before
    public void before() {
        connection = mock(Connection.class);
        when(manager.getConnection()).thenReturn(connection);
    }

    @Test
    public void getAllRecords() throws DaoException {
        when(expoDao.getAllRecords(1, 2, Query.ExpoSQL.SORT_BY_ID, connection)).thenReturn(TestEntity.Expo.expoList);
        assertDoesNotThrow(() -> expoService.getAllRecords(1, 2, Query.ExpoSQL.SORT_BY_ID));
    }

    @Test
    public void add() throws DaoException {
        when(expoDao.add(TestEntity.Expo.expoTest, connection)).thenReturn(TestEntity.Expo.expoTest);
        assertDoesNotThrow(() -> expoService.add(TestEntity.Expo.expoTest));

    }

    @Test
    public void update() throws DaoException {
        when(expoDao.update(TestEntity.Expo.expoTest, connection)).thenReturn(true);
        assertDoesNotThrow(() -> expoService.update(TestEntity.Expo.expoTest));
    }

    @Test
    public void searchExpoByTheme() throws DaoException {
        when(expoDao.searchExpo(Query.ExpoSQL.SEARCH_BY_THEME, "Job", connection))
                .thenReturn(Collections.singletonList(TestEntity.Expo.expoTest));
        assertDoesNotThrow(() -> expoService.searchExpo("searchByTheme", "Job"));
    }

    @Test
    public void searchExpoByDate() throws DaoException {
        LocalDate date = LocalDate.of(2022, 9, 13);
        when(expoDao.searchExpo(Query.ExpoSQL.SEARCH_BY_DATE, date, connection))
                .thenReturn(Collections.singletonList(TestEntity.Expo.expoTest));
        assertDoesNotThrow(() -> expoService.searchExpo("searchByDate", date));
    }

    @Test
    public void getCanceledExposForUser() throws DaoException {
        User user = User.builder().setIdUser(2).build();
        when(expoDao.getUserExpos(user, 2, 1, 5)).thenReturn(TestEntity.Expo.expoList);
        assertDoesNotThrow(() -> expoService.getUserExpos(user, 2, 1, 5));
    }

    @Test
    public void cancelExpo() throws DaoException {
        when(expoDao.changeStatus(1, 2, connection)).thenReturn(true);
        assertDoesNotThrow(() -> expoService.changeStatus(1, 2));
    }
}