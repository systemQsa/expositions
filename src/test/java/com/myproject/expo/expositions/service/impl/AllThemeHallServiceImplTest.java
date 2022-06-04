package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.AllThemeHallDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.impl.AllThemeHallDaoImpl;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.generator.TestEntity;
import com.myproject.expo.expositions.service.AllThemeHallService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllThemeHallServiceImplTest {
    private static AllThemeHallDao allThemeHallDao;
    private static AllThemeHallService allThemeHallService;
    private static ConnectManager manager;
    private static Connection connection;

    @BeforeClass
    public static void init() {
        allThemeHallDao = mock(AllThemeHallDaoImpl.class);
        manager = mock(ConnectManager.class);
        connection = mock(Connection.class);
        allThemeHallService = new AllThemeHallServiceImpl(allThemeHallDao, manager);
    }

    @Test
    public void allHalls() throws DaoException {
        when(manager.getConnection()).thenReturn(connection);
        when(allThemeHallDao.allHalls(connection)).thenReturn(TestEntity.HallTest.hallList);
        assertDoesNotThrow(() -> allThemeHallService.allHalls());
    }

    @Test
    public void allThemes() throws DaoException {
        when(allThemeHallDao.allThemes(manager.getConnection())).thenReturn(TestEntity.ThemeTest.themeList);
        assertDoesNotThrow(() -> allThemeHallService.allThemes());
    }
}