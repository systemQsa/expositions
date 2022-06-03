package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.entity_idao.ThemeDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.impl.connection.DBManager;
import com.myproject.expo.expositions.exception.DaoException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ThemeDaoImplTest {
    private static ThemeDao themeDao;
    private final Theme themeJobbing = Theme.builder()
            .setIDTheme(2)
            .setThemeName("jobbing")
            .build();

    @BeforeClass
    public static void init() {
        DBManager.getInstance().loadScript();
    }

    @Before
    public void before() {
        ConnectManager connectManager = DBManager.getInstance();
        themeDao = new ThemeDaoImpl(connectManager);
    }


    @Test
    public void getAllRecords() throws DaoException {
        List<Theme> allRecords = themeDao.getAllRecords(1, 4, "");
        assertEquals(4, allRecords.size());

    }

    @Test
    public void add() {
        assertDoesNotThrow(() -> themeDao.add(Theme.builder().setThemeName("Weather").build()));
    }

    @Test
    public void update() throws DaoException {
        assertThat(themeDao.update(themeJobbing)).isTrue();
    }

    public void remove() {
        assertDoesNotThrow(() -> themeDao.remove(4));
    }
}