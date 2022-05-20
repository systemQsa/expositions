package com.myproject.expo.expositions.factory;

public interface AbstractFactory {
    DaoFactory getDaoFactory();

    ServiceFactory getServiceFactory();
}
