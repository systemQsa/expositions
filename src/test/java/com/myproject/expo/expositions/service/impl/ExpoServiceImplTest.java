package com.myproject.expo.expositions.service.impl;

import com.myproject.expo.expositions.dao.ExpositionDao;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.impl.ExpositionDaoImpl;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.DaoException;
import com.myproject.expo.expositions.service.ExpositionService;
import com.myproject.expo.expositions.validation.Validate;
import com.myproject.expo.expositions.validation.ValidateInput;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

public class ExpoServiceImplTest {
    private static ExpositionDao expoDao;
    private static ExpositionService<Exposition> expoService;
    private static Validate validate;
    private static final List<Exposition> expoList = Arrays.asList(
            Exposition.builder()
                    .setExpositionID(10)
                    .setExpoName("expo1")
                    .build(),
            Exposition.builder()
                    .setExpositionID(11)
                    .setExpoName("expo2")
                    .build()
    );
    private static final Exposition expoTest = Exposition.builder()
            .setExpositionID(15)
            .setExpoName("Science")
            .setExpoDate(LocalDate.parse("2022-09-13"))
            .setExpoTime(LocalTime.parse("17:30:00"))
            .setExpoPrice(BigDecimal.valueOf(250.00))
           // .setHall(Hall.builder().setIDHall(1).build())
            .setTheme(Theme.builder().setIDTheme(2).build())
            .setTickets(25)
            .build();

    @BeforeClass
    public static void init() {
        expoDao = mock(ExpositionDaoImpl.class);
        validate = mock(ValidateInput.class);
        expoService = new ExpoServiceImpl(expoDao,validate);
    }

    @Test
    public void remove() throws Exception {
        when(expoDao.remove(1)).thenReturn(true);
        assertDoesNotThrow(() -> expoService.remove(1));

        verify(expoService).remove(1);
    }

    @Test
    public void getAllRecords() throws DaoException {
        when(expoDao.getAllRecords(1, 2, Query.ExpoSQL.SORT_BY_ID)).thenReturn(expoList);
        assertDoesNotThrow(() -> expoService.getAllRecords(1, 2, Query.ExpoSQL.SORT_BY_ID));
    }

    @Test
    public void add() throws DaoException {
        when(expoDao.add(expoTest)).thenReturn(expoTest);
        assertDoesNotThrow(() -> expoService.add(expoTest));

    }

    @Test
    public void update() throws DaoException {
        when(expoDao.update(expoTest)).thenReturn(true);
        assertDoesNotThrow(() -> expoService.update(expoTest));
    }
}