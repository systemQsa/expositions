package com.myproject.expo.expositions.factory;

import com.myproject.expo.expositions.dao.entity_idao.ExpositionDao;
import com.myproject.expo.expositions.dao.entity_idao.HallDao;
import com.myproject.expo.expositions.dao.entity_idao.ThemeDao;
import com.myproject.expo.expositions.dao.entity_idao.UserDao;

public interface DaoFactory {

    UserDao getUserDao();

    ThemeDao getThemeDao();

    HallDao getHallDao();

    ExpositionDao getExpoDao();
}
