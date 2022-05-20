package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.HallDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.impl.connection.DBManager;
import com.myproject.expo.expositions.exception.DaoException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HallDaoImplTest {
    private static HallDao hallDao;
    private static final Hall addHall = Hall.builder()
            .setHallName("GG5")
            .build();

    private static final Hall updateHall = Hall.builder()
            .setIDHall(1)
            .setHallName("Spaceship")
            .build();

    @BeforeClass
    public static void init() {
        DBManager.getInstance().loadScript();
    }

    @Before
    public void before() {
        ConnectManager connectManager = DBManager.getInstance();
        hallDao = new HallDaoImpl(connectManager);
    }

    @Test
    public void add() {
        assertDoesNotThrow(() -> hallDao.add(addHall));
    }

    @Test
    public void getAllRecords() throws DaoException {
        List<Hall> hallList = hallDao.getAllRecords(1, 3,"");
        assertEquals(3, hallList.size());
    }

    @Test
    public void update() throws DaoException {
        assertThat(hallDao.update(updateHall)).isTrue();
    }

    @Test
    public void remove() {
        assertDoesNotThrow(() -> hallDao.remove(3));
    }
}