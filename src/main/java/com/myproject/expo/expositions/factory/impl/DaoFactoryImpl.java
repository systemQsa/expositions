package com.myproject.expo.expositions.factory.impl;

import com.myproject.expo.expositions.dao.ExpositionDao;
import com.myproject.expo.expositions.dao.HallDao;
import com.myproject.expo.expositions.dao.ThemeDao;
import com.myproject.expo.expositions.dao.UserDao;
import com.myproject.expo.expositions.dao.impl.ExpositionDaoImpl;
import com.myproject.expo.expositions.dao.impl.HallDaoImpl;
import com.myproject.expo.expositions.dao.impl.ThemeDaoImpl;
import com.myproject.expo.expositions.dao.impl.UserDaoImpl;
import com.myproject.expo.expositions.factory.DaoFactory;

public class DaoFactoryImpl implements DaoFactory {
    @Override
    public UserDao getUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public ThemeDao getThemeDao() {
        return new ThemeDaoImpl();
    }

    @Override
    public HallDao getHallDao() {
        return new HallDaoImpl();
    }

    @Override
    public ExpositionDao getExpoDao() {
        return new ExpositionDaoImpl();
    }
}
