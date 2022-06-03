package com.myproject.expo.expositions.command.user;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.Exposition;
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
import java.util.List;

public class BuyExpoCommand implements Command {
    private static final Logger logger = LogManager.getLogger(BuyExpoCommand.class);
    private final UserService userService;
    private final ServiceFactory serviceFactory;

    public BuyExpoCommand() {
        serviceFactory = new AbstractFactoryImpl().getServiceFactory();
        userService = serviceFactory.getUserService();
    }

    public BuyExpoCommand(UserService userService, ServiceFactory serviceFactory) {
        this.userService = userService;
        this.serviceFactory = serviceFactory;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        User user = (User) req.getSession().getAttribute(Constant.USER_DATA);
        try {
            userService.buyExpo(user, findRequiredExpoToBuy(req));
            req.getSession().setAttribute(Constant.USER_DATA, user);
            cleanSession(req);
            setInfMSGToSession(Constant.InfoMsg.BUY_SUCCESS, req);
            return Route.setFullRoutePath(Constant.URL.USER_REDIRECT, Route.RouteType.REDIRECT);
        } catch (ServiceException e) {
            setInfMSGToSession(e.getMessage(), req);
            logger.info(Constant.LogMsg.BUY_EXPO);
            throw new CommandException(Constant.URL.USER_REDIRECT);
        }
    }

    private Exposition findRequiredExpoToBuy(HttpServletRequest req) {
        List<Exposition> expoList = (List<Exposition>) req.getSession().getAttribute(Constant.EXPOS_LIST);
        long expoId = parseStrToLong(req.getParameter(Constant.EXPO_ID));
        return expoList.stream()
                .filter(item -> item.getIdExpo() == expoId)
                .findFirst()
                .orElse(null);
    }
}
