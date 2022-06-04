package com.myproject.expo.expositions.service.entity_iservice;

import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.service.RemovableService;
import com.myproject.expo.expositions.service.Service;

public interface ThemeService extends Service<Theme>, RemovableService {

    default String defineSortQueryForTheme(String str) {
        switch (str) {
            case "byName":
                return Query.ThemeSQL.GET_THEMES_BY_NAME;
            default:
                return Query.ThemeSQL.GET_ALL_THEMES_DEFAULT;
        }
    }
}
