package com.myproject.expo.expositions.command;

public class Route {
    private RouteType route;
    private String pathOfThePage;

    public RouteType getRoute() {
        return route;
    }

    public void setRoute(RouteType route) {
        if (route == null) {
            route = RouteType.REDIRECT;
        }
        this.route = route;
    }

    public String getPathOfThePage() {
        return pathOfThePage;
    }

    public void setPathOfThePage(String pathOfThePage) {
        if (this.pathOfThePage == null) {
            this.pathOfThePage = pathOfThePage;
        }
    }

    public static Route setFullRoutePath(String pagePath, RouteType routeType) {
        Route resRoute = new Route();
        resRoute.setRoute(routeType);
        resRoute.setPathOfThePage(pagePath);
        return resRoute;
    }

    public enum RouteType {
        REDIRECT, FORWARD
    }
}