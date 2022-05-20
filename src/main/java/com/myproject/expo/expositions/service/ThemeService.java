package com.myproject.expo.expositions.service;

import com.myproject.expo.expositions.dao.entity.Theme;

public interface ThemeService extends Service<Theme> {

    default String defineSortQueryForTheme(String str) {
        switch (str) {
            case "byName":
                return "SELECT * FROM theme ORDER BY name DESC LIMIT ?,?";
            default:
                return "SELECT * FROM theme ORDER BY id_theme DESC LIMIT ?,?";
        }
    }
}
