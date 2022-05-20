package com.myproject.expo.expositions.factory;

import com.myproject.expo.expositions.dao.ExpositionDao;
import com.myproject.expo.expositions.dao.HallDao;
import com.myproject.expo.expositions.dao.ThemeDao;
import com.myproject.expo.expositions.dao.UserDao;

public interface DaoFactory {

    UserDao getUserDao();

    ThemeDao getThemeDao();

    HallDao getHallDao();

    ExpositionDao getExpoDao();
}
