package com.myproject.expo.expositions.command.user;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.ServiceFactory;
import com.myproject.expo.expositions.factory.impl.ServiceFactoryImpl;
import com.myproject.expo.expositions.service.ExpositionService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCanceledExpos implements Command {
    private static final Logger logger = LogManager.getLogger(GetCanceledExpos.class);
    private final ExpositionService<Exposition> expoService;
    private final ServiceFactory serviceFactory;

    public GetCanceledExpos() {
        serviceFactory = new ServiceFactoryImpl();
        expoService = serviceFactory.getExpoService();
    }

    public GetCanceledExpos(ServiceFactory serviceFactory, ExpositionService<Exposition> expoService) {
        this.serviceFactory = serviceFactory;
        this.expoService = expoService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constant.USER_DATA);
        session.setAttribute(Constant.SEARCHED_LIST,null);
        try {
            session.setAttribute(Constant.CANCELED_EXPOS,expoService.getCanceledExposForUser(user,2,1,3));
        } catch (ServiceException e) {
            logger.info("GetCanceledExpos class has failed");
            setInformMessageToUser(19,req,e.getMessage());
            throw new CommandException(Constant.URL.FULL_USER_PAGE);
        }
        return Route.setFullRoutePath(Constant.URL.FULL_USER_PAGE, Route.RouteType.FORWARD);
    }
}
