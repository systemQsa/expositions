package com.myproject.expo.expositions.dao.impl.connection;

import com.myproject.expo.expositions.dao.connection.ConnectManager;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager implements ConnectManager {
    private static final Logger logger = LogManager.getLogger(DBManager.class);
    private static DBManager dbManager;
    private Connection connection;

    public static DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    @Override
    public Connection getConnection() {
        Connection connect = null;
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2db?user=root&password=1111");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = connect;
        return connection;
    }

    @Override
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.warn("connection close failed in tests");
            }

        }
    }

    @Override
    public void rollBack(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.warn("connection rollback failed in tests");
            }
        }
    }

    public void loadScript(){
        Properties properties = new Properties();
        String url = loadClassLoaderAndProperties(properties);
        try (Connection connection = DriverManager.getConnection(url)) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader("database/testdb.sql"));
            scriptRunner.setLogWriter(null);
            scriptRunner.runScript(reader);
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String loadClassLoaderAndProperties(Properties properties) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (OutputStream output = new FileOutputStream("connect.properties")) {
            properties.load(classLoader.getResourceAsStream("connect.properties"));
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("connection.url");
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
