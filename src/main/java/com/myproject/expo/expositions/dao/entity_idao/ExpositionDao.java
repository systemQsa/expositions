package com.myproject.expo.expositions.dao.entity_idao;

import com.myproject.expo.expositions.dao.*;
import com.myproject.expo.expositions.dao.entity.Exposition;

public interface ExpositionDao extends GeneralDao<Exposition>, Removable, Updatable<Exposition>, ChangeExpoStatusDao, GetUserExpos,SearchableDao{
}
