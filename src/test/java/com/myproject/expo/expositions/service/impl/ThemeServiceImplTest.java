package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.ThemeDao;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.impl.ThemeDaoImpl;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.ThemeService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ThemeServiceImplTest {
    private static ThemeService themeService;
    private static ThemeDao themeDao;
    private static final List<Theme> themeList = Arrays.asList(Theme.builder().setThemeName("science").build(),
            Theme.builder().setThemeName("theatre").build(),
            Theme.builder().setThemeName("books").build());
    public static final Theme updateTheme = Theme.builder()
            .setIDTheme(1)
            .setThemeName("Books")
            .build();

    @Before
    public void before()  {
        themeDao = mock(ThemeDaoImpl.class);
        themeService = new ThemeServiceImpl(themeDao);
    }

    @Test
    public void getAllRecords() throws ServiceException, DaoException {
        when(themeDao.getAllRecords(1, 3,"SELECT * FROM theme ORDER BY id_theme DESC LIMIT ?,?")).thenReturn(themeList);
        List<Theme> tempList = themeService.getAllRecords(1, 3,"");

        assertEquals(3, tempList.size());
    }

    @Test
    public void addTheme() {
        assertDoesNotThrow(() -> themeService.add(Theme.builder().setThemeName("Movies").build()));
        assertDoesNotThrow(() -> themeService.add(Theme.builder().setThemeName("Science").build()));
    }

    @Test
    public void remove(){
        assertDoesNotThrow(() -> themeService.remove(4));
    }

    @Test
    public void update() throws DaoException {
        when(themeDao.update(updateTheme)).thenReturn(true);
        assertDoesNotThrow(() -> themeService.update(updateTheme));
    }
}