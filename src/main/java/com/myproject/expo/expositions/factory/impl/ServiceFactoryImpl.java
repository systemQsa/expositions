package com.myproject.expo.expositions.factory.impl;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.factory.ServiceFactory;
import com.myproject.expo.expositions.service.ExpositionService;
import com.myproject.expo.expositions.service.HallService;
import com.myproject.expo.expositions.service.ThemeService;
import com.myproject.expo.expositions.service.UserService;
import com.myproject.expo.expositions.service.impl.ExpoServiceImpl;
import com.myproject.expo.expositions.service.impl.HallServiceImpl;
import com.myproject.expo.expositions.service.impl.ThemeServiceImpl;
import com.myproject.expo.expositions.service.impl.UserServiceImpl;

public class ServiceFactoryImpl implements ServiceFactory {
    @Override
    public UserService getUserService() {
        return new UserServiceImpl();
    }

    @Override
    public ThemeService gerThemeService() {
        return new ThemeServiceImpl();
    }

    @Override
    public HallService getHallService() {
        return new HallServiceImpl();
    }

    @Override
    public ExpositionService<Exposition> getExpoService() {
        return new ExpoServiceImpl();
    }
}
