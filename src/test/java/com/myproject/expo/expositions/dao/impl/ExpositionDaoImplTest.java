package com.myproject.expo.expositions.dao.impl;

import com.myproject.expo.expositions.dao.ExpositionDao;
import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.impl.connection.DBManager;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

public class ExpositionDaoImplTest {
    private static final Exposition expo = Exposition.builder()
            .setExpoName("Weather")
            .setExpoDate(LocalDate.parse("2022-05-13"))
            .setExpoTime(LocalTime.parse("18:44:23"))
            .setExpoPrice(BigDecimal.valueOf(135.00))
            .setExpoSoldTickets(5)
            //.setHall(Hall.builder().setIDHall(1).build())
            .setTheme(Theme.builder().setIDTheme(2).build())
            .setTickets(25)
            .build();

    private static final Exposition expoAdd = Exposition.builder()
            .setExpoName("Science")
            .setExpoDate(LocalDate.parse("2022-09-13"))
            .setExpoTime(LocalTime.parse("17:30:00"))
            .setExpoPrice(BigDecimal.valueOf(250.00))
           // .setHall(Hall.builder().setIDHall(1).build())
            .setTheme(Theme.builder().setIDTheme(2).build())
            .setTickets(25)
            .build();

    private ExpositionDao expoDao;
    private String q = "SELECT id_expo,e.name,expo_date,expo_time,price,sold,id_hall_ref,h.name,id_theme_ref,t.name,tickets FROM exposition as e JOIN hall as h ON id_hall_ref=h.id_hall JOIN theme as t ON id_theme_ref=t.id_theme ORDER BY id_expo DESC LIMIT ?,?";

    @BeforeClass
    public static void init() {
        DBManager.getInstance().loadScript();
    }

    @Before
    public void before() {
        ConnectManager connectManager = DBManager.getInstance();
        expoDao = new ExpositionDaoImpl(connectManager);
    }

    @Test
    public void getAllRecords() throws DaoException {
        List<Exposition> allRecords = expoDao.getAllRecords(1, 3, Query.ExpoSQL.SORT_BY_ID);
        Assertions.assertEquals(3, allRecords.size());

    }

    @Test
    public void update() {
        Assertions.assertDoesNotThrow(() -> expoDao.update(expo));
    }

    @Test
    public void add() {
        Assertions.assertDoesNotThrow(() -> expoDao.add(expoAdd));
    }

    @Test
    public void remove() throws Exception {
       assertTrue(expoDao.remove(3));
    }
}