package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.UserDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.entity.UserRole;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;


public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private final ConnectManager connectManager;
    private Connection connection;

    public UserDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    public UserDaoImpl(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    @Override
    public User add(User user) throws DaoException {
        connection = connectManager.getConnection();
        try (PreparedStatement ps =
                     connection.prepareStatement(Query.UserSQL.REGISTER_USER, Statement.RETURN_GENERATED_KEYS)) {
            user.setIdUser(getRoleIdForUserAfterRegister(setStatementForRegistration(ps, user)));
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.PROBLEM_REGISTER_USER_IN_DAO);
            throw new DaoException(Constant.ErrMsg.REGISTER);
        } finally {
            connectManager.closeConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> getAllRecords(long page, long noOfRecords, String querySortBy) throws DaoException {
        //todo implement
        return null;
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
        connection = connectManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(Query.UserSQL.GET_USER_BY_EMAIL)) {
            return extractFoundedUserFromResultSet(setStatementToFindUser(ps, email))
                    .filter(obj -> obj.getEmail() != null)
                    .orElseThrow(() -> new DaoException(Constant.ErrMsg.CANNOT_FIND_SUCH_USER));
        } catch (SQLException e) {
            throw new DaoException(Constant.ErrMsg.CANNOT_FIND_SUCH_USER);
        } finally {
            connectManager.closeConnection(connection);
        }
    }

    private ResultSet setStatementToFindUser(PreparedStatement statement, String email) throws SQLException {
        statement.setString(1, email);
      //  statement.setString(2, pass);
        return statement.executeQuery();
    }

    private Optional<User> extractFoundedUserFromResultSet(ResultSet resSet) throws SQLException {
        User.UserBuilder user = User.builder();
        if (resSet.next()) {
            user.setIdUser(resSet.getLong(1));
            user.setName(resSet.getString("name"));
            user.setSurname(resSet.getString("surname"));
            user.setEmail(resSet.getString("email"));
            user.setPassword(resSet.getString("password").toCharArray());
            user.setPhone(resSet.getString("phone"));
            user.setBalance(resSet.getBigDecimal("balance"));
            user.setUserRole(UserRole.defineUserRole(resSet.getInt("role_id")));
        }
        return Optional.ofNullable(user.build());
    }

    @Override
    public User updateBalance(User user, BigDecimal price) throws DaoException {
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(Query.UserSQL.UPDATE_USER_BALANCE)) {
            prepareStatementToTopUpUserBalance(user.getIdUser(), setNewUserBalance(user, price), statement);
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            logger.warn("User " + user.getIdUser() + " cannot top up balance!");
            throw new DaoException("err.top_up_balance");
        } finally {
            connectManager.closeConnection(connection);
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
        connection = connectManager.getConnection();
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
        } finally {
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
        throw new DaoException("err.no_more_tickets");
    }

    private User withdrawMoney(User user, Exposition expo) throws DaoException {
        BigDecimal balance = user.getBalance().subtract(expo.getPrice());
        if (balance.doubleValue() <= 0) {
            throw new DaoException("err.balance");
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
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("err.set_balance_statement");
        }
        return true;
    }

    private boolean setStatementToReferExpoUser(User user, Exposition expo, PreparedStatement statement2) throws DaoException {
        try {
            statement2.setLong(1, expo.getIdExpo());
            statement2.setLong(2, user.getIdUser());
            statement2.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("err.set_refs");
        }
        return true;
    }

    @Override
    public boolean changeEmail(String oldEmail, String newEmail) throws DaoException {
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(Query.UserSQL.UPDATE_USER_EMAIL)) {
            setStatementToUpdateTheEmail(oldEmail, newEmail, statement);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("User with email = " + oldEmail + " can`t update the email");
            throw new DaoException("err.change_email");
        }finally {
            connectManager.closeConnection(connection);
        }
    }

    private void setStatementToUpdateTheEmail(String oldEmail, String newEmail, PreparedStatement statement) throws SQLException {
        statement.setString(1, newEmail);
        statement.setString(2, oldEmail);
    }

    @Override
    public boolean changePass(String email, String password) throws DaoException {
        connection = connectManager.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(Query.UserSQL.UPDATE_USER_PASS)){
            setStatementToUpdateThePass(email, password, statement);
            return executeUpdatingUserPass(statement);
        }catch (SQLException | DaoException e){
            logger.warn("User with email = " + email + " can`t update the pass");
            throw new DaoException("err.change_pass");
        }finally {
            connectManager.closeConnection(connection);
        }
    }

    private boolean executeUpdatingUserPass(PreparedStatement statement) throws SQLException, DaoException {
        if (statement.executeUpdate() > 0){
            return true;
        }else {
            throw new DaoException("err.change_pass");
        }
    }

    private void setStatementToUpdateThePass(String email, String password, PreparedStatement statement) throws SQLException {
        statement.setString(1,password);
        statement.setString(2,email);
    }
}
