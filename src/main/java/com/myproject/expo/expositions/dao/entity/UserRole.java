package com.myproject.expo.expositions.dao.entity;

import java.util.Arrays;

public enum UserRole {
    ADMIN(1, "admin"), USER(2, "user"), GUEST(3, "guest");

    private final int id;
    private final String role;

    UserRole(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public static UserRole defineUserRole(int roleId) {
        return Arrays.stream(UserRole.values())
                .filter(role -> role.getId() == roleId)
                .findFirst()
                .orElse(null);

    }
}
