package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.AllThemeHallDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.impl.connection.DBManager;
import com.myproject.expo.expositions.exception.DaoException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AllThemeHallDaoImplTest {
    private AllThemeHallDao allThemeHallDao;

    @BeforeClass
    public static void init(){
        DBManager.getInstance().loadScript();
    }

    @Before
    public void before(){
        ConnectManager connectManager = DBManager.getInstance();
        allThemeHallDao = new AllThemeHallDaoImpl(connectManager);
    }

    @Test
    public void allHalls() throws DaoException {
        Assertions.assertEquals(3,allThemeHallDao.allHalls().size());
    }

    @Test
    public void allThemes() throws DaoException {
        Assertions.assertEquals(4,allThemeHallDao.allThemes().size());
    }
}