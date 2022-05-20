package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.AbstractFactory;
import com.myproject.expo.expositions.factory.ServiceFactory;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.HallService;
import com.myproject.expo.expositions.service.ThemeService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PrepareToAddExpo implements Command {
    private static final Logger logger = LogManager.getLogger(PrepareToAddExpo.class);
    private final HallService hallService;
    private final ThemeService themeService;
    private final ServiceFactory serviceFactory;

    public PrepareToAddExpo() {
        serviceFactory = new AbstractFactoryImpl().getServiceFactory();
        hallService = serviceFactory.getHallService();
        themeService = serviceFactory.gerThemeService();

    }

    public PrepareToAddExpo(ServiceFactory serviceFactory, HallService hallService, ThemeService themeService) {
        this.serviceFactory = serviceFactory;
        this.hallService = hallService;
        this.themeService = themeService;

    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        long noOfRecords = parseStrToLong(req.getParameter(Constant.NO_OF_RECORDS));
        String sortBy = setInitSortBy(req);
        HttpSession session = req.getSession();
        try {
            hallService.setListOfFoundedRecordsToTheSession(session,hallService.getAllRecords(1,noOfRecords,sortBy),1,noOfRecords);
            themeService.setListOfFoundedRecordsToTheSession(session,themeService.getAllRecords(1,noOfRecords,sortBy),1,noOfRecords);
        } catch (ServiceException e) {
            logger.info("PrepareToAddExpo failed");
            setInformMessageToUser(14,req,e.getMessage());
          throw new CommandException(Constant.URL.ADD_NEW_EXPO_FULL_PATH);
        }
        return Route.setFullRoutePath(Constant.URL.ADD_EXPO_REDIRECT, Route.RouteType.REDIRECT);
    }
}
