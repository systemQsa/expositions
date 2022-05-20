package com.myproject.expo.expositions.service;


import com.myproject.expo.expositions.dao.entity.Hall;

public interface HallService extends Service<Hall> {

    default String defineSortQueryForHall(String str) {
        switch (str) {
            case "byName":
                return "SELECT * FROM hall ORDER BY name DESC LIMIT ?,?";
            default:
                return "SELECT * FROM hall ORDER BY id_hall DESC LIMIT ?,?";
        }
    }
}
