package com.myproject.expo.expositions.factory;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.service.ExpositionService;
import com.myproject.expo.expositions.service.HallService;
import com.myproject.expo.expositions.service.ThemeService;
import com.myproject.expo.expositions.service.UserService;

public interface ServiceFactory {
    UserService getUserService();

    ThemeService gerThemeService();

    HallService getHallService();

    ExpositionService<Exposition> getExpoService();
}
