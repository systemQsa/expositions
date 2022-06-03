package com.myproject.expo.expositions.dao.entity_idao;

import com.myproject.expo.expositions.dao.GeneralDao;
import com.myproject.expo.expositions.dao.Removable;
import com.myproject.expo.expositions.dao.Updatable;
import com.myproject.expo.expositions.dao.entity.Theme;

public interface ThemeDao extends GeneralDao<Theme>, Removable, Updatable<Theme> {
}
