package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity_idao.ThemeDao;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.impl.ThemeDaoImpl;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.generator.TestEntity;
import com.myproject.expo.expositions.service.entity_iservice.ThemeService;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ThemeServiceImplTest {
    private static ThemeService themeService;
    private static ThemeDao themeDao;
    private Connection connection;
    private static final List<Theme> themeList =
            Arrays.asList(Theme.builder().setThemeName("science").build(),
                    Theme.builder().setThemeName("theatre").build(),
                    Theme.builder().setThemeName("books").build());

    @Before
    public void before() {
        themeDao = mock(ThemeDaoImpl.class);
        ConnectManager manager = mock(ConnectionPool.class);
        themeService = new ThemeServiceImpl(themeDao, manager);
        connection = mock(Connection.class);
        when(manager.getConnection()).thenReturn(connection);
    }

    @Test
    public void getAllRecords() throws ServiceException, DaoException {
        when(themeDao.getAllRecords(1, 3, Query.ThemeSQL.GET_ALL_THEMES_DEFAULT, connection)).thenReturn(themeList);
        List<Theme> tempList = themeService.getAllRecords(1, 3, "");

        assertEquals(3, tempList.size());
    }

    @Test
    public void addTheme() {
        assertDoesNotThrow(() -> themeService.add(Theme.builder().setThemeName("Movies").build()));
        assertDoesNotThrow(() -> themeService.add(Theme.builder().setThemeName("Science").build()));
    }

    @Test
    public void remove() {
        assertDoesNotThrow(() -> themeService.remove(4));
    }

    @Test
    public void update() throws DaoException {
        when(themeDao.update(TestEntity.ThemeTest.updateTheme, connection)).thenReturn(true);
        assertDoesNotThrow(() -> themeService.update(TestEntity.ThemeTest.updateTheme));
    }
}