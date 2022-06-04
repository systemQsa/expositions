package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.connection.ConnectManager;
import com.myproject.expo.expositions.dao.connection.ConnectionPool;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity_idao.HallDao;
import com.myproject.expo.expositions.dao.impl.HallDaoImpl;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.generator.TestEntity;
import com.myproject.expo.expositions.service.entity_iservice.HallService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HallServiceImplTest {
    private static HallService hallService;
    private static HallDao hallDao;
    private static Connection connection;
    private static ConnectManager connectManager;
    private static final List<Hall> hallList =
            Arrays.asList(Hall.builder().setIDHall(1).setHallName("G5").build(),
            Hall.builder().setIDHall(2).setHallName("A3").build(),
            Hall.builder().setIDHall(3).setHallName("AX5").build());

    @BeforeClass
    public static void init() {
        hallDao = mock(HallDaoImpl.class);
        connectManager = mock(ConnectionPool.class);
        hallService = new HallServiceImpl(hallDao,connectManager);

    }

    @Before
    public void before(){
        connection = mock(Connection.class);
        when(connectManager.getConnection()).thenReturn(connection);
    }

    @Test
    public void remove() throws Exception {
        when(hallDao.remove(23,connection)).thenReturn(true);
        assertDoesNotThrow(() -> hallService.remove(23));

    }

    @Test
    public void getAllRecords() throws DaoException, ServiceException {
        when(hallDao.getAllRecords(1, 3,
                Query.HallSQL.GET_ALL_HALLS_PAGINATE,connection)).thenReturn(hallList);
        List<Hall> allRecords = hallService.getAllRecords(1, 3,"");
        assertEquals(3, allRecords.size());

    }

    @Test
    public void add() throws DaoException {
        when(hallDao.add(Hall.builder().setHallName("FGFGH").build(),connection)).thenReturn(TestEntity.HallTest.addHall);
        assertDoesNotThrow(() -> hallService.add(Hall.builder().setHallName("FGFGH").build()));
    }

    @Test(expected = ServiceException.class)
    public void addNegative() throws ServiceException, DaoException {
        when(hallDao.add(Hall.builder().setHallName("A1").build(), connection)).thenThrow(new DaoException("err.add_exist_theme"));
        hallService.add(Hall.builder().setHallName("A1").build());
        verify(hallService).add(Hall.builder().setHallName("A1").build());
    }

    @Test(expected = ServiceException.class)
    public void update() throws ServiceException, DaoException {
        when(hallDao.update(TestEntity.HallTest.updateHall,connection)).thenThrow(DaoException.class);
        hallService.update(TestEntity.HallTest.updateHall);
        verify(hallService).update(TestEntity.HallTest.updateHall);
    }
}