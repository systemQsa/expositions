package com.myproject.expo.expositions.factory.impl;

import com.myproject.expo.expositions.factory.AbstractFactory;
import com.myproject.expo.expositions.factory.DaoFactory;
import com.myproject.expo.expositions.factory.ServiceFactory;

public class AbstractFactoryImpl implements AbstractFactory {

    @Override
    public DaoFactory getDaoFactory() {
        return new DaoFactoryImpl();
    }

    @Override
    public ServiceFactory getServiceFactory() {
        return new ServiceFactoryImpl();
    }
}
