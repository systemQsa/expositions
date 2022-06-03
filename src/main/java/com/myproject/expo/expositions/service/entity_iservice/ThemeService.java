package com.myproject.expo.expositions.service.entity_iservice;

import com.myproject.expo.expositions.dao.Removable;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.service.Service;

public interface ThemeService extends Service<Theme>, Removable {

    default String defineSortQueryForTheme(String str) {
        switch (str) {
            case "byName":
                return "SELECT * FROM theme ORDER BY name DESC LIMIT ?,?";
            default:
                return "SELECT * FROM theme ORDER BY id_theme DESC LIMIT ?,?";
        }
    }
}
