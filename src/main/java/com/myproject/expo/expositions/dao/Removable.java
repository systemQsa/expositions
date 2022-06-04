package com.myproject.expo.expositions.dao;

import java.sql.Connection;

public interface Removable {
    boolean remove(long id, Connection connection) throws Exception;
}
