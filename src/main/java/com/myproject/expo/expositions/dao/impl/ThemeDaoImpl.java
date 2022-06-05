package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.entity_idao.ThemeDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThemeDaoImpl implements ThemeDao {
    private static final Logger logger = LogManager.getLogger(ThemeDaoImpl.class);
    private final ConnectManager connectManager;

    public ThemeDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    public ThemeDaoImpl(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    @Override
    public List<Theme> getAllRecords(long page, long noOfRecords, String querySortBy, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(Query.ThemeSQL.GET_ALL_THEMES_PAGINATE)) {
            return createThemeListFromStatement(statement, page, noOfRecords);
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.GET_ALL_THEMES);
            throw new DaoException(Constant.ErrMsg.GET_ALL_THEMES);
        } finally {
            connectManager.closeConnection(connection);
        }
    }

    private List<Theme> createThemeListFromStatement(PreparedStatement statement, long page, long noOfRecords) throws SQLException {
        ResultSet resultSet;
        List<Theme> themeList = new ArrayList<>();
        statement.setLong(1, calcStartOffset(page, noOfRecords));
        statement.setLong(2, noOfRecords);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            themeList.add(buildThemeFromResultSet(resultSet));
        }
        return themeList;
    }

    private Theme buildThemeFromResultSet(ResultSet resSet) throws SQLException {
        return Theme.builder()
                .setIDTheme(resSet.getLong(1))
                .setThemeName(resSet.getString(2))
                .build();
    }

    @Override
    public Theme add(Theme theme, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(Query.ThemeSQL.ADD_NEW_THEME, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, theme.getName());
            return setGeneratedIdToTheTheme(theme, statement);
        } catch (SQLException e) {
            logger.warn(Constant.LogMsg.ADD_NEW_THEME);
            throw new DaoException(Constant.ErrMsg.ADD_NEW_THEME);
        } finally {
            connectManager.closeConnection(connection);
        }
    }

    private Theme setGeneratedIdToTheTheme(Theme theme, PreparedStatement statement) throws SQLException {
        if (statement.executeUpdate() > 0) {
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                theme.setIdTheme(set.getLong(1));
            }
        }
        return theme;
    }

    @Override
    public boolean update(Theme theme, Connection connection) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(Query.ThemeSQL.UPDATE_THEME)) {
            setStatementToUpdateTheTheme(theme, statement);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(Constant.ErrMsg.UPDATE_THEME);
        } finally {
            connectManager.closeConnection(connection);
        }
    }

    private void setStatementToUpdateTheTheme(Theme theme, PreparedStatement statement) throws SQLException {
        statement.setString(1, theme.getName());
        statement.setLong(2, theme.getIdTheme());
    }

    @Override
    public boolean remove(long idTheme, Connection connection) throws Exception {
        try (PreparedStatement checkIfThemePresentInExpo = connection.prepareStatement(Query.ThemeSQL.CHECK_THEME_PRESENT_IN_EXPO);
             PreparedStatement statement = connection.prepareStatement(Query.ThemeSQL.DELETE_THEME)) {
            return deletingThemeProcess(idTheme, connection, checkIfThemePresentInExpo, statement);
        } catch (SQLException e) {
            throw new DaoException(Constant.ErrMsg.DELETE_THEME);
        } finally {
            connectManager.closeConnection(connection);
        }
    }

    private boolean deletingThemeProcess(long idTheme, Connection connection, PreparedStatement checkIfThemePresentInExpo,
                                         PreparedStatement statement) throws SQLException {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        checkIfThemePresentInExpo.setLong(1, idTheme);
        ResultSet resultSet = checkIfThemePresentInExpo.executeQuery();
        deleteTheme(idTheme, connection, statement, resultSet);
        connection.commit();
        return true;
    }

    private boolean deleteTheme(long idTheme, Connection connection, PreparedStatement statement,
                                ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            statement.setLong(1, idTheme);
            return statement.executeUpdate() > 0;
        } else {
            connection.rollback();
            throw new SQLException("");
        }
    }
}
