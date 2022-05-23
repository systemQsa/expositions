package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.HallDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HallDaoImpl implements HallDao {
    private static final Logger logger = LogManager.getLogger(HallDaoImpl.class);
    private final ConnectManager connectManager;
    private Connection connection;

    public HallDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    public HallDaoImpl(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    @Override
    public Hall add(Hall hall) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.HallSQL.ADD_NEW_HALL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, hall.getName());
            return setGeneratedIdToTheHall(hall, statement);
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.ADD_HALL);
            throw new DaoException(Constant.ErrMsg.ADD_HALL);
        }
    }

    private Hall setGeneratedIdToTheHall(Hall hall, PreparedStatement statement) throws SQLException {
        if (statement.executeUpdate() > 0) {
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                hall.setIdHall(set.getLong(1));
            }
        }
        return hall;
    }

    @Override
    public List<Hall> getAllRecords(long page, long noOfRecords, String querySortBy) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.HallSQL.GET_ALL_HALLS_PAGINATE)) {
            return createHallListFromStatement(page, noOfRecords, statement);
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.GET_ALL_HALLS);
            throw new DaoException(Constant.ErrMsg.GET_ALL_HALLS);
        }
    }

    private List<Hall> createHallListFromStatement(long page, long noOfRecords, PreparedStatement statement) throws SQLException {
        ResultSet resSet;
        List<Hall> hallList = new ArrayList<>();
        statement.setLong(1, calcStartOffset(page, noOfRecords));
        statement.setLong(2, noOfRecords);
        resSet = statement.executeQuery();
        while (resSet.next()) {
            hallList.add(buildHall(resSet));
        }
        return hallList;
    }

    private Hall buildHall(ResultSet resSet) throws SQLException {
        return Hall.builder()
                .setIDHall(resSet.getInt(1))
                .setHallName(resSet.getString(2))
                .build();
    }

    @Override
    public boolean update(Hall hall) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.HallSQL.UPDATE_HALL)) {
            setStatementToUpdateTheHall(hall, statement);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.UPDATE_HALL + hall.getIdHall());
            throw new DaoException(Constant.ErrMsg.UPDATE_HALL);
        }
    }

    private void setStatementToUpdateTheHall(Hall hall, PreparedStatement statement) throws SQLException {
        statement.setString(1, hall.getName());
        statement.setLong(2, hall.getIdHall());
    }

    @Override
    public boolean remove(long idHall) throws Exception {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.HallSQL.DELETE_HALL)) {
            statement.setLong(1, idHall);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.DELETE_HALL + idHall);
            throw new DaoException(Constant.ErrMsg.DELETE_HALL);
        }
    }
}
