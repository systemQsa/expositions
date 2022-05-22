package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
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

public class BlockUnblockUser implements Command {
    private static final Logger logger = LogManager.getLogger(BlockUnblockUser.class);
    private final UserService userService;
    private final ServiceFactory serviceFactory;

    public BlockUnblockUser() {
        serviceFactory = new ServiceFactoryImpl();
        userService = serviceFactory.getUserService();
    }

    public BlockUnblockUser(ServiceFactory serviceFactory, UserService userService) {
        this.serviceFactory = serviceFactory;
        this.userService = userService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String status = req.getParameter(Constant.STATUS);
        long userId = parseStrToLong(req.getParameter(Constant.Param.USER_ID));
        cleanSession(req);
        req.getSession().setAttribute(Constant.USERS_LIST,null);
        try {
            userService.blockUnblockUser(status, userId);
            req.getSession().setAttribute(Constant.USERS_LIST,userService.getAllUsers(1,5));
        } catch (ServiceException e) {
            setInformMessageToUser(21, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
        return Route.setFullRoutePath(Constant.URL.ADMIN_REDIRECT, Route.RouteType.REDIRECT);
    }
}
