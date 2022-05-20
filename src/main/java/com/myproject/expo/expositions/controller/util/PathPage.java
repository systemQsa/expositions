package com.myproject.expo.expositions.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public enum PathPage {
    USER_HOME_PAGE("/views/user/user.jsp", "/WEB-INF/views/user/user.jsp"),
    ADMIN_HOME_PAGE("/views/admin/admin.jsp", "/WEB-INF/views/admin/admin.jsp"),
    ADMIN_ADD_NEW_EXPO("/views/admin/addNewExpo.jsp","/WEB-INF/views/admin/addNewExpo.jsp");
    private final String path;
    private final String URI;

    PathPage(String URI, String path) {
        this.URI = URI;
        this.path = path;
    }

    public static String getRequiredPagePath(HttpServletRequest req) {
        return Arrays.stream(PathPage.values())
                .filter(pathPage -> req.getRequestURI().contains(pathPage.getURI()))
                .map(PathPage::getPath)
                .findFirst()
                .orElse(null);
    }

    public static String getRequiredPagePath(String URI) {
        return Arrays.stream(PathPage.values())
                .filter(pathPage -> pathPage.getURI().equals(URI))
                .map(PathPage::getPath)
                .findFirst()
                .orElse(null);
    }

    public String getURI() {
        return URI;
    }

    public String getPath() {
        return path;
    }
}
