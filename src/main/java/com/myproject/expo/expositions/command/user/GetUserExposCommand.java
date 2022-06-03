package com.myproject.expo.expositions.command.user;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.ServiceFactory;
import com.myproject.expo.expositions.factory.impl.ServiceFactoryImpl;
import com.myproject.expo.expositions.service.entity_iservice.ExpositionService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetUserExposCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetUserExposCommand.class);
    private final ExpositionService<Exposition> expoService;
    private final ServiceFactory serviceFactory;

    public GetUserExposCommand() {
        serviceFactory = new ServiceFactoryImpl();
        expoService = serviceFactory.getExpoService();
    }

    public GetUserExposCommand(ServiceFactory serviceFactory, ExpositionService<Exposition> expoService) {
        this.serviceFactory = serviceFactory;
        this.expoService = expoService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        HttpSession session = cleanSession(req);
        User user = (User) session.getAttribute(Constant.USER_DATA);
        long page = parseStrToLong(req.getParameter(Constant.PAGE));
        long noOfRecords = parseStrToLong(req.getParameter(Constant.NO_OF_RECORDS));
        int statusId = getStatusId(req.getParameter(Constant.STATUS));
        try {
            session.setAttribute(Constant.USER_EXPOS, expoService.getUserExpos(user, statusId, page, noOfRecords));
            req.setAttribute(Constant.LIST_HEADER, setHeaderForActiveCanceledUserExpos(req.getParameter(Constant.STATUS), req));
        } catch (ServiceException e) {
            setInformMessageToUser(19, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_USER_PAGE);
        }
        return Route.setFullRoutePath(Constant.URL.FULL_USER_PAGE, Route.RouteType.FORWARD);
    }

    private String setHeaderForActiveCanceledUserExpos(String viewListOf, HttpServletRequest req) {
        if (viewListOf.equals(Constant.ACTIVE)) {
            return translateInfoMessageToRequiredLang(Constant.InfoMsg.MY_ACTIVE_EXPOS, req);
        } else {
            setInformMessageToUser(22, req, Constant.InfoMsg.CONTACT_MANAGER);
            return translateInfoMessageToRequiredLang(Constant.InfoMsg.MY_CANCELED_EXPOS, req);
        }

    }

}
