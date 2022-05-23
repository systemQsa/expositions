package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.UserDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.Status;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.entity.UserRole;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private final ConnectManager connectManager;

    public UserDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    public UserDaoImpl(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    @Override
    public User add(User user) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(Query.UserSQL.REGISTER_USER, Statement.RETURN_GENERATED_KEYS)) {
            user.setIdUser(getRoleIdForUserAfterRegister(setStatementForRegistration(ps, user)));
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.PROBLEM_REGISTER_USER_IN_DAO);
            throw new DaoException(Constant.ErrMsg.REGISTER);
        }
        return user;
    }

    private PreparedStatement setStatementForRegistration(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setString(3, user.getEmail());
        statement.setString(4, String.valueOf(user.getPassword()));
        statement.setString(5, user.getPhone());
        return statement;
    }

    private long getRoleIdForUserAfterRegister(PreparedStatement statement) throws SQLException {
        ResultSet resultSet;
        long id = 0;
        if (statement.executeUpdate() > 0) {
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
        }
        return id;
    }

    @Override
    public User getUserByEmailAndPass(String email, String pass) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(Query.UserSQL.GET_USER_BY_EMAIL)) {
            return extractFoundedUserFromResultSet(setStatementToFindUser(ps, email))
                    .filter(obj -> obj.getEmail() != null)
                    .orElseThrow(() -> new DaoException(Constant.ErrMsg.CANNOT_FIND_SUCH_USER));
        } catch (SQLException e) {
            throw new DaoException(Constant.ErrMsg.CANNOT_FIND_SUCH_USER);
        }
    }

    private ResultSet setStatementToFindUser(PreparedStatement statement, String email) throws SQLException {
        statement.setString(1, email);
        //TODO redone a little bit
        //  statement.setString(2, pass);
        return statement.executeQuery();
    }

    private Optional<User> extractFoundedUserFromResultSet(ResultSet resSet) throws SQLException {
        User.UserBuilder user = User.builder();
        if (resSet.next()) {
            user.setIdUser(resSet.getLong(1))
                    .setName(resSet.getString(Constant.Column.NAME))
                    .setSurname(resSet.getString(Constant.Column.SURNAME))
                    .setEmail(resSet.getString(Constant.Column.EMAIL))
                    .setPassword(resSet.getString(Constant.Column.PASSWORD).toCharArray())
                    .setPhone(resSet.getString(Constant.Column.PHONE))
                    .setBalance(resSet.getBigDecimal(Constant.Column.BALANCE))
                    .setUserRole(UserRole.defineUserRole(resSet.getInt(Constant.Column.ROLE_ID)))
                    .setStatus(defineStatus(resSet.getInt(Constant.Column.STATUS_ID)));
        }
        return Optional.ofNullable(user.build());
    }

    @Override
    public User updateBalance(User user, BigDecimal price) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.UserSQL.UPDATE_USER_BALANCE)) {
            prepareStatementToTopUpUserBalance(user.getIdUser(), setNewUserBalance(user, price), statement);
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.TOP_UP_BALANCE + user.getIdUser());
            throw new DaoException(Constant.ErrMsg.TOP_UP_BALANCE);
        }
    }

    private BigDecimal setNewUserBalance(User user, BigDecimal price) {
        user.setBalance(user.getBalance().add(price));
        return user.getBalance();
    }

    private void prepareStatementToTopUpUserBalance(long userId, BigDecimal price, PreparedStatement statement) throws SQLException {
        statement.setBigDecimal(1, price);
        statement.setLong(2, userId);
    }

    @Override
    public User buyExpo(User user, Exposition expo) throws DaoException {
        Connection connection = connectManager.getConnection();
        try (PreparedStatement updateUserBalance = connection.prepareStatement(Query.UserSQL.WITHDRAW_MONEY_AND_UPDATE_EXPOS_SOLD_TICKETS);
             PreparedStatement tieUserWithExpo = connection.prepareStatement(Query.UserSQL.INSERT_REFS_EXPO_USER)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            buyingProcess(user, expo, updateUserBalance, tieUserWithExpo);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connectManager.rollBack(connection);
            logger.warn("User " + user.getIdUser() + " cannot buy expo " + expo.getIdExpo());
            throw new DaoException(e.getMessage());
        }finally {
            connectManager.closeConnection(connection);
        }
        return user;
    }

    private void buyingProcess(User user, Exposition expo, PreparedStatement updateUserBalance, PreparedStatement tieUserWithExpo) throws DaoException {
        isTicketsPresent(expo);
        withdrawMoney(user, expo);
        setStatementToReferExpoUser(user, expo, tieUserWithExpo);
        setStatementToUpdateUserBalance(user, expo, updateUserBalance);
    }

    private boolean isTicketsPresent(Exposition expo) throws DaoException {
        if (expo.getTickets() - 1 > 0) {
            return true;
        }
        throw new DaoException(Constant.ErrMsg.NO_MORE_TICKETS);
    }

    private User withdrawMoney(User user, Exposition expo) throws DaoException {
        BigDecimal balance = user.getBalance().subtract(expo.getPrice());
        if (balance.doubleValue() <= 0) {
            throw new DaoException(Constant.ErrMsg.POOR_BALANCE);
        }
        user.setBalance(balance);
        return user;
    }

    private boolean setStatementToUpdateUserBalance(User user, Exposition expo, PreparedStatement statement) throws DaoException {
        try {
            statement.setBigDecimal(1, user.getBalance());
            statement.setLong(2, expo.getSold() + 1);
            statement.setLong(3, expo.getTickets() - 1);
            statement.setLong(4, expo.getIdExpo());
            statement.setLong(5, user.getIdUser());
           return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(Constant.ErrMsg.SET_BALANCE);
        }
    }

    private boolean setStatementToReferExpoUser(User user, Exposition expo, PreparedStatement statement2) throws DaoException {
        try {
            statement2.setLong(1, expo.getIdExpo());
            statement2.setLong(2, user.getIdUser());
           return statement2.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(Constant.ErrMsg.SET_REFS_EXPO_USER);
        }
    }

    @Override
    public boolean changeEmail(String oldEmail, String newEmail) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.UserSQL.UPDATE_USER_EMAIL)) {
            setStatementToUpdateTheEmail(oldEmail, newEmail, statement);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.CHANGE_EMAIL + oldEmail);
            throw new DaoException(Constant.ErrMsg.CHANGE_EMAIL);
        }
    }

    private void setStatementToUpdateTheEmail(String oldEmail, String newEmail, PreparedStatement statement) throws SQLException {
        statement.setString(1, newEmail);
        statement.setString(2, oldEmail);
    }

    @Override
    public boolean changePass(String email, String password) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.UserSQL.UPDATE_USER_PASS)) {
            setStatementToUpdateThePass(email, password, statement);
            return executeUpdatingUserPass(statement);
        } catch (SQLException | DaoException e) {
            logger.warn(Constant.LogMsg.CHANGE_PASSWORD + email);
            throw new DaoException(Constant.ErrMsg.CHANGE_PASSWORD);
        }
    }

    private boolean executeUpdatingUserPass(PreparedStatement statement) throws SQLException, DaoException {
        if (statement.executeUpdate() > 0) {
            return true;
        } else {
            throw new DaoException(Constant.ErrMsg.CHANGE_PASSWORD);
        }
    }

    private void setStatementToUpdateThePass(String email, String password, PreparedStatement statement) throws SQLException {
        statement.setString(1, password);
        statement.setString(2, email);
    }

    @Override
    public List<User> getAllRecords(long page, long noOfRecords, String query) throws DaoException {
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            return createListOfUsers(statement.executeQuery());
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.GET_ALL_USERS);
            throw new DaoException(Constant.ErrMsg.GET_ALL_USERS);
        }
    }

    private List<User> createListOfUsers(ResultSet resSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resSet.next()) {
            users.add(buildEachUser(resSet));
        }
        return users;
    }

    private User buildEachUser(ResultSet resSet) throws SQLException {
        return User.builder()
                .setIdUser(resSet.getLong(1))
                .setName(resSet.getString(Constant.Column.NAME))
                .setSurname(resSet.getString(Constant.Column.SURNAME))
                .setEmail(resSet.getString(Constant.Column.EMAIL))
                .setPhone(resSet.getString(Constant.Column.PHONE))
                .setUserRole(UserRole.defineUserRole(resSet.getInt(Constant.Column.ROLE_ID)))
                .setStatus(defineStatus(resSet.getInt(Constant.Column.STATUS_ID)))
                .build();
    }

    @Override
    public boolean blockUnblockUser(int newStatus, long userId) throws DaoException {
        checkStatusNotZero(newStatus);
        try (Connection connection = connectManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query.UserSQL.UPDATE_USER_STATUS)) {
            statement.setInt(1, newStatus);
            statement.setLong(2, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(Constant.ErrMsg.BLOCK_UNBLOCK_USER);
        }
    }

    private int checkStatusNotZero(int statusId) throws DaoException {
        if (statusId > 0) {
            return statusId;
        }
        throw new DaoException(Constant.ErrMsg.NO_SUCH_STATUS);
    }

}
