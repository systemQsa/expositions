package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.Exposition;
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

public class ChangeExpoStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeExpoStatusCommand.class);
    private final ExpositionService<Exposition> expoService;
    private final ServiceFactory serviceFactory;

    public ChangeExpoStatusCommand() {
        serviceFactory = new ServiceFactoryImpl();
        expoService = serviceFactory.getExpoService();
    }

    public ChangeExpoStatusCommand(ServiceFactory serviceFactory, ExpositionService<Exposition> expoService) {
        this.serviceFactory = serviceFactory;
        this.expoService = expoService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        long expoId = parseStrToLong(req.getParameter(Constant.EXPO_ID));
        cleanSession(req);
        try {
          expoService.changeStatus(expoId,getStatusId(req.getParameter(Constant.STATUS)));
          req.getSession().setAttribute(Constant.EXPOS_LIST,expoService.getAllRecords(1,2,Constant.ID ));
        } catch (ServiceException e) {
            setInformMessageToUser(13,req,e.getMessage());
            throw new CommandException(Constant.URL.SEE_ONE_EXPO_FULL_PATH_ADMIN);
        }
        return Route.setFullRoutePath(Constant.URL.ADMIN_REDIRECT, Route.RouteType.REDIRECT);
    }
}
