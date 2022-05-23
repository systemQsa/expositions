package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.AllThemeHallDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllThemeHallDaoImpl implements AllThemeHallDao {
    private static final Logger logger = LogManager.getLogger(AllThemeHallDaoImpl.class);
    private final ConnectManager connectManager;

    public AllThemeHallDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    public AllThemeHallDaoImpl(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    @Override
    public List<Hall> allHalls() throws DaoException {
        try (Connection connection = connectManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(Query.HallSQL.GET_ALL_HALLS);
            return createHallList(statement.executeQuery());
        } catch (SQLException e) {
             throw new DaoException(Constant.ErrMsg.GET_ALL_HALLS);
        }
    }

    private List<Hall> createHallList(ResultSet resSet) throws SQLException {
        List<Hall> list = new ArrayList<>();
        while (resSet.next()) {
            list.add(buildHall(resSet));
        }
        return list;
    }

    private Hall buildHall(ResultSet resSet) throws SQLException {
        return Hall.builder()
                .setIDHall(resSet.getLong(1))
                .setHallName(resSet.getString(Constant.Column.NAME))
                .build();
    }

    @Override
    public List<Theme> allThemes() throws DaoException {
        try (Connection connection = connectManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(Query.ThemeSQL.GET_ALL_THEMES)) {
             return createThemeList(statement.executeQuery());
        } catch (SQLException e) {
             throw new DaoException(Constant.ErrMsg.GET_ALL_THEMES);
        }
    }

    private List<Theme> createThemeList(ResultSet resSet) throws SQLException {
        List<Theme> list = new ArrayList<>();
        while (resSet.next()){
            list.add(buildTheme(resSet));
        }
        return list;
    }

    private Theme buildTheme(ResultSet resSet) throws SQLException {
        return Theme.builder()
                .setIDTheme(resSet.getLong(1))
                .setThemeName(resSet.getString(Constant.Column.NAME))
                .build();
    }
}
