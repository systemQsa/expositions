package com.myproject.expo.expositions.command.util;

import com.myproject.expo.expositions.controller.util.PathPage;

public class DefinePathForUser {

    public static String definePath(String role) {
        switch (role) {
            case "admin":
                return PathPage.ADMIN_HOME_PAGE.getURI();

            case "user":
                return PathPage.USER_HOME_PAGE.getURI();

            default:
                return "/index.jsp";
        }

    }
}
