package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.entity_idao.ExpositionDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExpositionDaoImpl implements ExpositionDao {
    private static final Logger logger = LogManager.getLogger(ExpositionDaoImpl.class);
    private Connection connection;
    private final ConnectManager connectManager;

    public ExpositionDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    public ExpositionDaoImpl(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    @Override
    public List<Exposition> getAllRecords(long page, long noOfRecords, String querySortBy) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySortBy)) {
            return buildListOfExpos(page, noOfRecords, statement);
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.CANT_GET_ALL_EXPOS);
            throw new DaoException(Constant.ErrMsg.CANT_GET_EXPOS);
        }
    }

    private List<Exposition> buildListOfExpos(long page, long noOfRecords, PreparedStatement stmt) throws SQLException {
        List<Exposition> exposList = new ArrayList<>();
        ResultSet resSet = prepareAndExecuteQueryFindAll(page, noOfRecords, stmt);
        while (resSet.next()) {
            exposList.add(createExpoFromResultSet(resSet));
        }
        return exposList;
    }

    private ResultSet prepareAndExecuteQueryFindAll(long page, long noOfRecords, PreparedStatement stmt) throws SQLException {
        stmt.setLong(1, calcStartOffset(page, noOfRecords));
        stmt.setLong(2, noOfRecords);
        return stmt.executeQuery();
    }

    @Override
    public List<Exposition> getUserExpos(User user, int statusId, long page, long noOfRecords) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.ExpoSQL.GET_CANCELED_EXPOS_FOR_USER)) {
            return gettingAllExposForUser(user, statusId, statement);
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.CANT_GET_USER_EXPOS + user.getIdUser());
            throw new DaoException(Constant.ErrMsg.GET_ALL_USER_EXPOS);
        }
    }

    private List<Exposition> gettingAllExposForUser(User user, int statusId, PreparedStatement statement) throws SQLException {
        List<Exposition> canceledExpos = new ArrayList<>();
        statement.setLong(1, user.getIdUser());
        statement.setInt(2, statusId);
        ResultSet resSet = statement.executeQuery();
        while (resSet.next()) {
            canceledExpos.add(createExpoFromResultSet(resSet));
        }
        return canceledExpos;
    }

    private Exposition createExpoFromResultSet(ResultSet resSet) throws SQLException {
        return Exposition.builder()
                .setExpositionID(resSet.getLong(1))
                .setExpoName(resSet.getString(Constant.Column.NAME))
                .setExpoDate(resSet.getDate(Constant.Column.EXPO_DATE).toLocalDate())
                .setExpoTime(resSet.getTime(Constant.Column.EXPO_TIME).toLocalTime())
                .setExpoPrice(resSet.getBigDecimal(Constant.Column.PRICE))
                .setExpoSoldTickets(resSet.getLong(Constant.Column.SOLD))
                .setTheme(createThemeFromResultSet(resSet))
                .setTickets(resSet.getLong(Constant.Column.TICKETS))
                .setStatusId(resSet.getInt(Constant.Column.STATUS_ID))
                .setHallList(createHallList(resSet))
                .build();

    }

    private Theme createThemeFromResultSet(ResultSet resSet) throws SQLException {
        return Theme.builder()
                .setIDTheme(resSet.getLong(Constant.Column.ID_THEME_REF))
                .setThemeName(resSet.getString(Constant.Column.T_NAME))
                .build();
    }

    private List<Hall> createHallList(ResultSet resSet) throws SQLException {
        List<Hall.HallBuilder> halls = createListBuilderWithHallIds(resSet);
        List<Hall> hallNames = createListWithHallNames(resSet);
        return halls.stream()
                .peek((hall) -> {
                    for (int i = 0; i < halls.size(); i++) {
                        halls.get(i).setHallName(hallNames.get(i).getName());
                    }
                })
                .map(Hall.HallBuilder::build)
                .collect(Collectors.toList());
    }

    private List<Hall.HallBuilder> createListBuilderWithHallIds(ResultSet resSet) throws SQLException {
        return Arrays.stream(resSet.getString(Constant.Column.IDS).split(", "))
                .map(val -> Hall.builder().setIDHall(Long.parseLong(val)))
                .collect(Collectors.toList());
    }

    private List<Hall> createListWithHallNames(ResultSet resSet) throws SQLException {
        return Arrays.stream(resSet.getString(Constant.Column.HALLS).split(", "))
                .map(value -> Hall.builder().setHallName(value).build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(Exposition expo) throws DaoException {
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(Query.ExpoSQL.UPDATE_EXPO);
             PreparedStatement deleteRefs = connection.prepareStatement(Query.ExpoSQL.DELETE_EXPO_FROM_EXPO_HALL_TABLE);
             PreparedStatement insertNewRefs = connection.prepareStatement(Query.ExpoSQL.INSERT_REFS_TO_EXPO_HALL_TABLE)) {
            updatingTheExpoProcess(expo, statement, deleteRefs, insertNewRefs);
        } catch (SQLException e) {
            connectManager.rollBack(connection);
            throw new DaoException(Constant.ErrMsg.UPDATE_EXPO);
        } finally {
            connectManager.closeConnection(connection);
        }
        return true;
    }

    private void updatingTheExpoProcess(Exposition expo, PreparedStatement statement, PreparedStatement deleteRefs, PreparedStatement insertNewRefs) throws SQLException {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        updateRequiredExpoCells(expo, statement);
        deleteDuplicatesFromExpoHallTableIfRequired(expo, deleteRefs);
        insertNewRefsForExpoHallTable(expo, insertNewRefs);
        connection.commit();
    }

    private boolean updateRequiredExpoCells(Exposition expo, PreparedStatement statement) throws SQLException {
        setStatementToUpdateOrAddExpo(expo, statement);
        setIdExpoToUpdate(expo, statement);
        return statement.executeUpdate() > 0;
    }

    private void deleteDuplicatesFromExpoHallTableIfRequired(Exposition expo, PreparedStatement deleteRefs) throws SQLException {
        deleteRefs.setLong(1, expo.getIdExpo());
        deleteRefs.executeUpdate();
    }

    private void insertNewRefsForExpoHallTable(Exposition expo, PreparedStatement insertNewRefs) throws SQLException {
        for (Hall hall : expo.getHallList()) {
            insertNewRefs.setLong(1, expo.getIdExpo());
            insertNewRefs.setLong(2, hall.getIdHall());
            insertNewRefs.executeUpdate();
        }
    }

    private void setIdExpoToUpdate(Exposition expo, PreparedStatement statement) throws SQLException {
        statement.setLong(8, expo.getIdExpo());
    }


    @Override
    public Exposition add(Exposition expo) throws DaoException {
        connection = connectManager.getConnection();
        try (PreparedStatement insertExpo = connection.prepareStatement(Query.ExpoSQL.ADD_NEW_EXPO, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertRefs = connection.prepareStatement(Query.ExpoSQL.INSERT_REFS_TO_EXPO_HALL_TABLE)) {
            return addingNewExpoProcess(expo, insertExpo, insertRefs);
        } catch (SQLException e) {
            connectManager.rollBack(connection);
            logger.warn(Constant.LogMsg.ADD_NEW_EXPO);
            throw new DaoException(Constant.ErrMsg.ADD_NEW_EXPO);
        } finally {
            connectManager.closeConnection(connection);
        }
    }

    private Exposition addingNewExpoProcess(Exposition expo, PreparedStatement insertExpo, PreparedStatement insertRefs) throws SQLException {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        addExpo(expo, insertExpo);
        insertRefsToExpoHallTable(expo, insertRefs);
        connection.commit();
        return expo;

    }

    private Exposition addExpo(Exposition expo, PreparedStatement insertExpo) throws SQLException {
        setStatementToUpdateOrAddExpo(expo, insertExpo);
        expo.setIdExpo(executeInsertionNewExpoGetKey(insertExpo));
        return expo;
    }

    private void insertRefsToExpoHallTable(Exposition expo, PreparedStatement insertRefs) throws SQLException {
        for (Hall hall : expo.getHallList()) {
            insertRefs.setLong(1, expo.getIdExpo());
            insertRefs.setLong(2, hall.getIdHall());
            insertRefs.executeUpdate();
        }
    }

    private void setStatementToUpdateOrAddExpo(Exposition expo, PreparedStatement statement) throws SQLException {
        statement.setString(1, expo.getName());
        statement.setDate(2, Date.valueOf(expo.getDate()));
        statement.setTime(3, Time.valueOf(expo.getTime()));
        statement.setBigDecimal(4, expo.getPrice());
        statement.setLong(5, expo.getSold());
        statement.setLong(6, expo.getTheme().getIdTheme());
        statement.setLong(7, expo.getTickets());
    }

    private long executeInsertionNewExpoGetKey(PreparedStatement insertExpo) throws SQLException {
        insertExpo.executeUpdate();
        ResultSet resSet = insertExpo.getGeneratedKeys();
        resSet.next();
        return resSet.getLong(1);

    }

    @Override
    public boolean remove(long id) throws Exception {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.ExpoSQL.DELETE_EXPO)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(Constant.ErrMsg.DELETE_EXPO);
        }
    }

    @Override
    public boolean changeStatus(long expoId, int statusId) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.ExpoSQL.UPDATE_EXPO_STATUS)) {
            statement.setInt(1, statusId);
            statement.setLong(2, expoId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.UPDATE_EXPO_STATUS + expoId);
            throw new DaoException(Constant.ErrMsg.CHANGE_EXPO_STATUS);
        }
    }

    @Override
    public List<Exposition> searchExpo(String query, String searchedItem) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, searchedItem);
            ResultSet resSet = statement.executeQuery();
            return buildListOfSearchedItems(resSet);
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.SEARCH_BY_NAME);
            throw new DaoException(Constant.ErrMsg.NOTHING_WAS_FOUND);
        }
    }

    @Override
    public List<Exposition> searchExpo(String query, LocalDate localDate) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(localDate));
            ResultSet resultSet = statement.executeQuery();
            return buildListOfSearchedItems(resultSet);
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.SEARCH_BY_DATE);
            throw new DaoException(Constant.ErrMsg.NOTHING_WAS_FOUND);
        }
    }

    private List<Exposition> buildListOfSearchedItems(ResultSet resultSet) throws SQLException {
        List<Exposition> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(createExpoFromResultSet(resultSet));
        }
        return list;
    }


}
