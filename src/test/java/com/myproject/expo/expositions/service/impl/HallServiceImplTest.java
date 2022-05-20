package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.HallDao;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.impl.HallDaoImpl;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.HallService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HallServiceImplTest {
    private static HallService hallService;
    private static HallDao hallDao;
    private static final List<Hall> hallList = Arrays.asList(Hall.builder().setIDHall(1).setHallName("G5").build(),
            Hall.builder().setIDHall(2).setHallName("A3").build(),
            Hall.builder().setIDHall(3).setHallName("AX5").build());
    private static final Hall updateHall = Hall.builder()
            .setIDHall(23)
            .setHallName("spices")
            .build();
    private static final Hall addHall = Hall.builder()
            .setIDHall(5)
            .setHallName("FGFGH")
            .build();

    @BeforeClass
    public static void init() {
        hallDao = mock(HallDaoImpl.class);
        hallService = new HallServiceImpl(hallDao);
    }

    @Test
    public void remove() throws Exception {
        when(hallDao.remove(23)).thenReturn(true);
        assertDoesNotThrow(() -> hallService.remove(23));

    }

    @Test
    public void getAllRecords() throws DaoException, ServiceException {
        when(hallDao.getAllRecords(1, 3,"SELECT * FROM hall ORDER BY id_hall DESC LIMIT ?,?")).thenReturn(hallList);
        List<Hall> allRecords = hallService.getAllRecords(1, 3,"");
        assertEquals(3, allRecords.size());

    }

    @Test
    public void add() throws DaoException {
        when(hallDao.add(Hall.builder().setHallName("FGFGH").build())).thenReturn(addHall);
        assertDoesNotThrow(() -> hallService.add(Hall.builder().setHallName("FGFGH").build()));
    }

    @Test(expected = ServiceException.class)
    public void addNegative() throws ServiceException, DaoException {
        when(hallDao.add(Hall.builder().setHallName("A1").build())).thenThrow(new DaoException("err.add_exist_theme"));
        hallService.add(Hall.builder().setHallName("A1").build());
        verify(hallService).add(Hall.builder().setHallName("A1").build());
    }

    @Test(expected = ServiceException.class)
    public void update() throws ServiceException, DaoException {
        when(hallDao.update(updateHall)).thenThrow(DaoException.class);
        hallService.update(updateHall);
        verify(hallService).update(updateHall);
    }
}