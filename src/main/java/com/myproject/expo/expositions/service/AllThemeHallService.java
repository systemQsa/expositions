package com.myproject.expo.expositions.service;

import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.exception.ServiceException;

import java.util.List;

public interface AllThemeHallService {
    List<Hall> allHalls() throws ServiceException;

    List<Theme> allThemes() throws ServiceException;
}
