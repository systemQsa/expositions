package com.myproject.expo.expositions.dao.connection;


import java.sql.Connection;

public interface ConnectManager {
    Connection getConnection();

    void closeConnection(Connection connection);

    void rollBack(Connection connection);
}
