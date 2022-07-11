package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.AllThemeHallDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.impl.AllThemeHallDaoImpl;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.AllThemeHallService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class AllThemeHallServiceImpl implements AllThemeHallService {
    private static final Logger logger = LogManager.getLogger(AllThemeHallServiceImpl.class);
    private final AllThemeHallDao allThemeHallDao;
    private final ConnectManager connectManager;

    public AllThemeHallServiceImpl() {
        allThemeHallDao = new AllThemeHallDaoImpl();
         connectManager = ConnectionPool.getInstance();
    }

    public AllThemeHallServiceImpl(AllThemeHallDao allThemeHallDao, ConnectManager manager) {
        this.allThemeHallDao = allThemeHallDao;
        this.connectManager = manager;
    }

    @Override
    public List<Hall> allHalls() throws ServiceException {
        try {
            return allThemeHallDao.allHalls(connectManager.getConnection());
        } catch (DaoException e) {
            logger.warn("Cannot get all halls in service layer");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Theme> allThemes() throws ServiceException {
        try {
            return allThemeHallDao.allThemes(connectManager.getConnection());
        } catch (DaoException e) {
            logger.warn("Cannot get all themes in service layer");
            throw new ServiceException(e.getMessage());
        }
    }
}
