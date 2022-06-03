package com.myproject.expo.expositions.command.general;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.ServiceFactory;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.entity_iservice.UserService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeEmailCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeEmailCommand.class);
    private final UserService userService;
    private final ServiceFactory serviceFactory;

    public ChangeEmailCommand() {
        serviceFactory = new AbstractFactoryImpl().getServiceFactory();
        userService = serviceFactory.getUserService();
    }

    public ChangeEmailCommand(UserService userService, ServiceFactory serviceFactory) {
        this.userService = userService;
        this.serviceFactory = serviceFactory;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String oldEmail = req.getParameter(Constant.OLD_EMAIL);
        String newEmail = req.getParameter(Constant.NEW_EMAIL);
        try {
            userService.changeEmail(oldEmail,newEmail);
        } catch (ServiceException e) {
            setInformMessageToUser(17,req,e.getMessage());
            throw new CommandException(Constant.URL.ROOT_FULL_PATH);
        }
        return Route.setFullRoutePath(Constant.URL.ROOT_FULL_PATH, Route.RouteType.REDIRECT);
    }
}
