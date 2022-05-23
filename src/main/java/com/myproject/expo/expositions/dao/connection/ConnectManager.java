package com.myproject.expo.expositions.dao.connection;


import java.sql.Connection;

public interface ConnectManager extends AutoCloseable{
    Connection getConnection();

    void closeConnection(Connection connection);

    void rollBack(Connection connection);
}
