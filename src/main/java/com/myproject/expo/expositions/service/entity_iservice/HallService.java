package com.myproject.expo.expositions.service.entity_iservice;


import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.service.RemovableService;
import com.myproject.expo.expositions.service.Service;

public interface HallService extends Service<Hall>, RemovableService {

    default String defineSortQueryForHall(String str) {
        switch (str) {
            case "byName":
                return "SELECT * FROM hall ORDER BY name DESC LIMIT ?,?";
            default:
                return "SELECT * FROM hall ORDER BY id_hall DESC LIMIT ?,?";
        }
    }
}
