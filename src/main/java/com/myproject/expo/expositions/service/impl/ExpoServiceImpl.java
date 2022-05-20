package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.ExpositionDao;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.ExpositionService;
import com.myproject.expo.expositions.util.Constant;
import com.myproject.expo.expositions.validation.Validate;
import com.myproject.expo.expositions.validation.ValidateInput;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class ExpoServiceImpl implements ExpositionService<Exposition> {
    private static final Logger logger = LogManager.getLogger(ExpoServiceImpl.class);
    private final ExpositionDao expoDao;
    private final Validate validate;

    public ExpoServiceImpl() {
        expoDao = new AbstractFactoryImpl().getDaoFactory().getExpoDao();
        validate = new ValidateInput();
    }

    public ExpoServiceImpl(ExpositionDao expoDao, Validate validate) {
        this.expoDao = expoDao;
        this.validate = validate;
    }

    @Override
    public boolean remove(long id) throws Exception {
        return false;
    }

    @Override
    public List<Exposition> getAllRecords(long page, long noOfPages, String sortBy) throws ServiceException {
        try {
            return Optional.ofNullable(expoDao.getAllRecords(page, noOfPages, defineSortQueryForExpo(sortBy)))
                    .filter(expositions -> expositions.size() != 0)
                    .orElseThrow(() -> new ServiceException("err.cant_get_expos"));
        } catch (DaoException e) {
            logger.warn("ExpoServiceImpl class has failed while getting all expos");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Exposition> getCanceledExposForUser(User user, int statusId, long page, long noOfRecords) throws ServiceException {
        try {
            return expoDao.getCanceledExposForUser(user, statusId, page, noOfRecords);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Exposition add(Exposition expo) throws ServiceException {
        try {
            validate.validateProperDatesAndTime(expo.getDate(), expo.getTime());
            return expoDao.add(expo);
        } catch (DaoException | ValidationException e) {
            logger.warn("ExpoServiceImpl class has failed while adding a new exposition");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(Exposition expo) throws ServiceException {
        try {
            return expoDao.update(expo);
        } catch (DaoException e) {
            logger.warn("ExpoServiceImpl class has failed while updating the Exposition");
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Exposition> searchExpo(String searchBy, String searchedItem) throws ServiceException {
        try {
            return Optional.ofNullable(expoDao.searchExpo(searchByGetQuery(searchBy), searchedItem))
                    .filter(list -> list.size() > 0)
                    .orElseThrow(() -> new DaoException("err.nothing_found"));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Exposition> searchExpo(String searchBy, LocalDate localDate) throws ServiceException {
        try {
            return Optional.ofNullable(expoDao.searchExpo(searchByGetQuery(searchBy), localDate))
                    .filter(list -> list.size() > 0)
                    .orElseThrow(() -> new DaoException("err.nothing_found"));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean cancelExpo(long expoId, int statusId) throws ServiceException {
        try {
            return expoDao.changeStatus(expoId, statusId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void setListOfFoundedRecordsToTheSession(HttpSession session, List<Exposition> list, long currPage, long noOfRecords) {
        Optional.ofNullable(list)
                .ifPresent(records -> {
                    session.setAttribute(Constant.EXPOS_LIST, new CopyOnWriteArrayList<>(records));
                    session.setAttribute(Constant.CURRENT_PAGE, currPage);
                    session.setAttribute(Constant.NO_OF_RECORDS, noOfRecords);
                });
    }
}
