package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity_idao.HallDao;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.RemovableService;
import com.myproject.expo.expositions.service.entity_iservice.HallService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class HallServiceImpl implements HallService, RemovableService {
    private static final Logger logger = LogManager.getLogger(HallServiceImpl.class);
    private final HallDao hallDao;
    private final ConnectManager manager;
    public HallServiceImpl() {
        hallDao = new AbstractFactoryImpl().getDaoFactory().getHallDao();
        manager = ConnectionPool.getInstance();
    }

    public HallServiceImpl(HallDao hallDao,ConnectManager connectManager) {
        this.hallDao = hallDao;
        this.manager = connectManager;
    }

    @Override
    public boolean remove(long id) throws ServiceException {
        try {
            return hallDao.remove(id, manager.getConnection());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Hall> getAllRecords(long page, long noOfPages,String sortBy) throws ServiceException {
        try {
            return Optional.ofNullable(hallDao.getAllRecords(page, noOfPages,defineSortQueryForHall(sortBy), manager.getConnection()))
                    .filter(list -> list.size() > 0)
                    .orElseThrow(() -> new ServiceException(Constant.ErrMsg.NO_MORE_RECORDS));
        } catch (DaoException e) {
            logger.warn("Getting all halls in HallServiceImpl class failed");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Hall add(Hall hall) throws ServiceException {
        try {
            return hallDao.add(hall, manager.getConnection());
        } catch (DaoException e) {
            logger.warn("Cant add a new hall in HallServiceImpl class");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(Hall hall) throws ServiceException {
        try {
            return hallDao.update(hall, manager.getConnection());
        } catch (DaoException e) {
            logger.warn("Cannot update given Hall in HallServiceImpl class");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void setListOfFoundedRecordsToTheSession(HttpSession session, List<Hall> list) {
        Optional.ofNullable(list)
                .ifPresent(records -> session.setAttribute(Constant.HALL_LIST, new CopyOnWriteArrayList<>(records)));
    }
}
