package com.myproject.expo.expositions.dao.connection;

import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool implements ConnectManager {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static volatile ConnectionPool instance;

    private ConnectionPool() {}

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Connection getConnection()  {
        Connection connection = null;
        try {
            Context contextInit = new InitialContext();
            Context contextLookUp = (Context) contextInit.lookup(Constant.LOOK_UP_ENV);
            connection = ((DataSource) contextLookUp.lookup(Constant.JDBC_DATA)).getConnection();
        } catch (NamingException | SQLException e) {
            logger.fatal(Constant.LogMsg.CONNECTION_TO_DB_FAILED);
        }
        return connection;
    }

    @Override
    public void closeConnection(Connection connection)  {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                logger.fatal(Constant.LogMsg.CANNOT_CLOSE_CONNECTION);
            }
        }
    }

    @Override
    public void rollBack(Connection connection)  {
       if (connection != null){
           try {
               connection.rollback();
           } catch (SQLException e) {
               logger.warn(Constant.LogMsg.CANNOT_ROLLBACK_COMMIT);
           }
       }
    }
}
