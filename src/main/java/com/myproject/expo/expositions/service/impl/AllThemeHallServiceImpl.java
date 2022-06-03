package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.AllThemeHallDao;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.impl.AllThemeHallDaoImpl;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.AllThemeHallService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class AllThemeHallServiceImpl implements AllThemeHallService {
    private static final Logger logger = LogManager.getLogger(AllThemeHallServiceImpl.class);
    private final AllThemeHallDao allThemeHallDao;

    public AllThemeHallServiceImpl() {
        allThemeHallDao = new AllThemeHallDaoImpl();
    }

    public AllThemeHallServiceImpl(AllThemeHallDao allThemeHallDao) {
        this.allThemeHallDao = allThemeHallDao;
    }

    @Override
    public List<Hall> allHalls() throws ServiceException {
        try {
            return allThemeHallDao.allHalls();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Theme> allThemes() throws ServiceException {
        try {
            return allThemeHallDao.allThemes();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
