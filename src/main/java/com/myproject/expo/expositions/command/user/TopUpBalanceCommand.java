package com.myproject.expo.expositions.command.user;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.UtilMethods;
import com.myproject.expo.expositions.dao.entity.User;
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

/**
 * The TopUpBalanceCommand class executes top up user balance
 */
public class TopUpBalanceCommand implements Command, UtilMethods {
    private static final Logger logger = LogManager.getLogger(TopUpBalanceCommand.class);
    private final UserService userService;
    private final ServiceFactory serviceFactory;

    public TopUpBalanceCommand() {
        serviceFactory = new AbstractFactoryImpl().getServiceFactory();
        userService = serviceFactory.getUserService();
    }

    public TopUpBalanceCommand(UserService userService, ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        this.userService = userService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        User user = (User) req.getSession().getAttribute(Constant.USER_DATA);
        try {
            userService.updateBalance(user, parseToBigDecimal(req.getParameter(Constant.PRICE)));
            req.getSession().setAttribute(Constant.USER_DATA, user);
        } catch (ServiceException e) {
            setInformMessageToUser(16, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_USER_PAGE);
        }
        return Route.setFullRoutePath(Constant.URL.USER_REDIRECT, Route.RouteType.REDIRECT);
    }
}
