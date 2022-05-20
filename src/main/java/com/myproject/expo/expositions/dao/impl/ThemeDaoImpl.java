package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.ThemeDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThemeDaoImpl implements ThemeDao {
    private static final Logger logger = LogManager.getLogger(ThemeDaoImpl.class);
    private final ConnectManager connectManager;
    private Connection connection;

    public ThemeDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    public ThemeDaoImpl(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    @Override
    public List<Theme> getAllRecords(long page, long noOfRecords, String querySortBy) throws DaoException {
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM theme ORDER BY id_theme DESC LIMIT ?,?")) {
            return createThemeListFromStatement(statement, page, noOfRecords);
        } catch (SQLException e) {
            logger.warn("Impossible to get All Themes from db");
            throw new DaoException("err.all_themes");
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
    public Theme add(Theme theme) throws DaoException {
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO theme(name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, theme.getName());
            return setGeneratedIdToTheTheme(theme, statement);
        } catch (SQLException e) {
            logger.warn("Cannot add new theme to the db");
            throw new DaoException("err.add_new_theme");
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
    public boolean update(Theme theme) throws DaoException {
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("UPDATE theme SET name=? WHERE id_theme=?")) {
            setStatementToUpdateTheTheme(theme, statement);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("err.cannot_update_theme");
        } finally {
            connectManager.closeConnection(connection);
        }
    }

    private void setStatementToUpdateTheTheme(Theme theme, PreparedStatement statement) throws SQLException {
        statement.setString(1, theme.getName());
        statement.setLong(2, theme.getIdTheme());
    }

    @Override
    public boolean remove(long idTheme) throws Exception {
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM theme WHERE id_theme=?")) {
            statement.setLong(1, idTheme);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("err.delete_theme");
        } finally {
            connectManager.closeConnection(connection);
        }
    }
}
