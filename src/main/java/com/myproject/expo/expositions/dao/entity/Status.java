package com.myproject.expo.expositions.dao.entity;


public enum Status {
    ACTIVE(1, "active"), CANCELED(2, "canceled"), BLOCKED(3, "blocked");

    private final int statusId;
    private final String name;

    Status(int statusId, String name) {
        this.statusId = statusId;
        this.name = name;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getName() {
        return name;
    }
}
