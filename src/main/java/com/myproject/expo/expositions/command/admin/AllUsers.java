package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.ServiceFactory;
import com.myproject.expo.expositions.factory.impl.ServiceFactoryImpl;
import com.myproject.expo.expositions.service.UserService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AllUsers implements Command {
    private static final Logger logger = LogManager.getLogger(AllUsers.class);
    private final UserService userService;
    private final ServiceFactory serviceFactory;

    public AllUsers() {
        serviceFactory = new ServiceFactoryImpl();
        userService = serviceFactory.getUserService();
    }

    public AllUsers(ServiceFactory serviceFactory, UserService userService) {
        this.serviceFactory = serviceFactory;
        this.userService = userService;
    }


    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        HttpSession session = cleanSession(req);
        long page = parseStrToLong(req.getParameter(Constant.PAGE));
        long noOfRecords = parseStrToLong(req.getParameter(Constant.NO_OF_RECORDS));
        try {
            List<User> allUsers = userService.getAllUsers(page, noOfRecords);
            session.setAttribute(Constant.USERS_LIST, allUsers);
        } catch (ServiceException e) {
            logger.info("Getting all registered user has failed");
            setInformMessageToUser(20, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
        return Route.setFullRoutePath(Constant.URL.FULL_ADMIN_PAGE, Route.RouteType.FORWARD);
    }
}
