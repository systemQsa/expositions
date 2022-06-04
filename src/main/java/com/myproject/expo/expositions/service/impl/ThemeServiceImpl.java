package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity_idao.ThemeDao;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.entity_iservice.ThemeService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThemeServiceImpl implements ThemeService {
    private static final Logger logger = LogManager.getLogger(ThemeServiceImpl.class);
    private final ThemeDao themeDao;
   private final ConnectManager manager;
    public ThemeServiceImpl() {
        themeDao = new AbstractFactoryImpl().getDaoFactory().getThemeDao();
        manager = ConnectionPool.getInstance();
    }

    public ThemeServiceImpl(ThemeDao themeDao,ConnectManager manager) {
        this.themeDao = themeDao;
        this.manager = manager;
    }

    @Override
    public List<Theme> getAllRecords(long page, long noOfPages,String sortBy) throws ServiceException {
        try {
            return Optional.ofNullable(themeDao.getAllRecords(page, noOfPages,defineSortQueryForTheme(sortBy), manager.getConnection()))
                    .filter(res -> res.size() != 0)
                    .orElseThrow(() -> new ServiceException(Constant.ErrMsg.NO_MORE_RECORDS));
        } catch (DaoException e) {
            logger.warn("Getting all themes in ThemeServiceImpl class failed");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Theme add(Theme theme) throws ServiceException {
        try {
            return themeDao.add(theme, manager.getConnection());
        } catch (DaoException e) {
            logger.warn("Cant add a new theme in ThemeService class");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(Theme theme) throws ServiceException {
        try {
            return themeDao.update(theme, manager.getConnection());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean remove(long id) throws ServiceException {
        try {
            return themeDao.remove(id, manager.getConnection());
        } catch (Exception e) {
            logger.warn("Cannot remove the given theme in ThemeServiceImpl class");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void setListOfFoundedRecordsToTheSession(HttpSession session, List<Theme> list) {
        Optional.ofNullable(list)
                .ifPresent(records -> session.setAttribute(Constant.THEME_LIST, new CopyOnWriteArrayList<>(records)));
    }
}
