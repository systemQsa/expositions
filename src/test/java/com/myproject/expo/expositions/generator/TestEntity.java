package com.myproject.expo.expositions.generator;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class TestEntity {
    public static class Expo {
        public static final Exposition expo = Exposition.builder()
                .setExpositionID(1)
                .setExpoName("Weather")
                .setExpoDate(LocalDate.parse("2022-05-13"))
                .setExpoTime(LocalTime.parse("18:44:23"))
                .setExpoPrice(BigDecimal.valueOf(135.00))
                .setExpoSoldTickets(5)
                .setHallList(Collections.singletonList(Hall.builder().setIDHall(1).build()))
                .setTheme(Theme.builder().setIDTheme(2).build())
                .setTickets(25)
                .build();
        public static final Exposition expoAdd = Exposition.builder()
                .setExpoName("Science")
                .setExpoDate(LocalDate.parse("2022-09-13"))
                .setExpoTime(LocalTime.parse("17:30:00"))
                .setExpoPrice(BigDecimal.valueOf(250.00))
                .setHallList(Collections.singletonList(Hall.builder().setIDHall(1).build()))
                .setTheme(Theme.builder().setIDTheme(3).build())
                .setTickets(25)
                .build();
        public static final List<Exposition> expoList = Arrays.asList(
                Exposition.builder()
                        .setExpositionID(10)
                        .setExpoName("expo1")
                        .build(),
                Exposition.builder()
                        .setExpositionID(11)
                        .setExpoName("expo2")
                        .build()
        );
        public static final Exposition expoTest = Exposition.builder()
                .setExpositionID(15)
                .setExpoName("Science")
                .setExpoDate(LocalDate.parse("2022-09-13"))
                .setExpoTime(LocalTime.parse("17:30:00"))
                .setExpoPrice(BigDecimal.valueOf(250.00))
                .setTheme(Theme.builder().setIDTheme(2)
                        .setThemeName("Job").build())
                .setTickets(25)
                .build();
    }

    public static class HallTest {
        public static final Hall addHall = Hall.builder()
                .setHallName("GG5")
                .build();
        public static final Hall updateHall = Hall.builder()
                .setIDHall(1)
                .setHallName("Spaceship")
                .build();
        public static final List<Hall> hallList = Collections.singletonList(Hall
                .builder()
                .setHallName("hallName")
                .build());
    }

    public static class ThemeTest {
        public static Theme themeJobbing = Theme.builder()
                .setIDTheme(2)
                .setThemeName("jobbing")
                .build();
        public static final Theme addTheme = Theme.builder()
                .setThemeName("themeName")
                .build();
        public static final List<Theme>themeList = Collections.singletonList(Theme
                .builder()
                .setThemeName("themeName")
                .build());
        public static final Theme updateTheme = Theme.builder()
                .setIDTheme(1)
                .setThemeName("Books")
                .build();
    }

    public static class UserTest {
        public static User user = User.builder()
                .setName("name")
                .setSurname("surname")
                .setEmail("user23@gmail.com")
                .setPassword(new char[]{'1', '2', '3'})
                .setBalance(new BigDecimal("500.0"))
                .setPhone("+234567823")
                .build();
        public static User updateUser = User.builder()
                .setIdUser(2)
                .setName("Jessica")
                .setSurname("Thompson")
                .setEmail("jess@gmail.com")
                .setPhone("+1234567888")
                .setBalance(new BigDecimal("500.0"))
                .build();
        public static final Exposition buyExpo = Exposition.builder()
                .setExpositionID(2)
                .setTickets(45)
                .setExpoPrice(new BigDecimal("300.00"))
                .setExpoSoldTickets(15)
                .build();
        public static final User getUser = User.builder()
                .setName("name")
                .setEmail("user@gmail.com")
                .setPhone("+324566789795")
                .setPassword(new char[]{'1', '2', '3'})
                .setBalance(new BigDecimal("500.0"))
                .build();
    }

}
