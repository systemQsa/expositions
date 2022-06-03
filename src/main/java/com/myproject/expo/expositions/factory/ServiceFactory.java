package com.myproject.expo.expositions.factory;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.service.entity_iservice.ExpositionService;
import com.myproject.expo.expositions.service.entity_iservice.HallService;
import com.myproject.expo.expositions.service.entity_iservice.ThemeService;
import com.myproject.expo.expositions.service.entity_iservice.UserService;

public interface ServiceFactory {
    UserService getUserService();

    ThemeService gerThemeService();

    HallService getHallService();

    ExpositionService<Exposition> getExpoService();
}
