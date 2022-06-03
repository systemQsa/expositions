package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.AllThemeHallDao;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.impl.AllThemeHallDaoImpl;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.service.AllThemeHallService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
public class AllThemeHallServiceImplTest {
    private static AllThemeHallDao allThemeHallDao;
    private static AllThemeHallService allThemeHallService;
    private static final List<Hall> hallList = Collections.singletonList(Hall
            .builder()
            .setHallName("hallName")
            .build());
    private static final List<Theme>themeList = Collections.singletonList(Theme
            .builder()
            .setThemeName("themeName")
            .build());

    @BeforeClass
    public static void init(){
        allThemeHallDao = mock(AllThemeHallDaoImpl.class);
        allThemeHallService = new AllThemeHallServiceImpl(allThemeHallDao);
    }

    @Test
    public void allHalls() throws DaoException {
        when(allThemeHallDao.allHalls()).thenReturn(hallList);
        assertDoesNotThrow(() -> allThemeHallService.allHalls());
    }

    @Test
    public void allThemes() throws DaoException {
        when(allThemeHallDao.allThemes()).thenReturn(themeList);
        assertDoesNotThrow(() -> allThemeHallService.allThemes());
    }
}