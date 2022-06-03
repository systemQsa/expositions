package com.myproject.expo.expositions.service.entity_iservice;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.sql.Query;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.Searchable;
import com.myproject.expo.expositions.service.Service;

import java.util.List;

public interface ExpositionService<T> extends Service<T>, Searchable {
    List<Exposition> getUserExpos(User user, int statusId, long page, long noOfRecords) throws ServiceException;

    boolean changeStatus(long expoId, int statusId) throws ServiceException;

    default String defineSortQueryForExpo(String str) {
        switch (str) {
            case "byPrice":
                return Query.ExpoSQL.SORT_BY_PRICE;
            case "byTheme":
                return Query.ExpoSQL.SORT_BY_THEME;
            case "byDate":
                return Query.ExpoSQL.SORT_BY_DATE;
            case "statistic":
                return Query.ExpoSQL.STATISTICS;
            default:
                return Query.ExpoSQL.SORT_BY_ID;
        }
    }

    default String searchByGetQuery(String searchBy) {
        switch (searchBy) {
            case "searchByTheme":
                return Query.ExpoSQL.SEARCH_BY_THEME;
            case "searchByDate":
                return Query.ExpoSQL.SEARCH_BY_DATE;
            default:
                return "";
        }
    }
}
