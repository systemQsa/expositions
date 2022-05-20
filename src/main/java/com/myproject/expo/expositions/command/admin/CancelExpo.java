package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.Exposition;
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

public class CancelExpo implements Command {
    private static final Logger logger = LogManager.getLogger(CancelExpo.class);
    private final ExpositionService<Exposition> expoService;
    private final ServiceFactory serviceFactory;

    public CancelExpo() {
        serviceFactory = new ServiceFactoryImpl();
        expoService = serviceFactory.getExpoService();
    }

    public CancelExpo(ServiceFactory serviceFactory, ExpositionService<Exposition> expoService) {
        this.serviceFactory = serviceFactory;
        this.expoService = expoService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        long expoId = parseStrToLong(req.getParameter(Constant.EXPO_ID));
        int statusId = Integer.parseInt(req.getParameter("statusId"));
        try {
            expoService.cancelExpo(expoId,statusId);
        } catch (ServiceException e) {
            setInformMessageToUser(13,req,e.getMessage());
            throw new CommandException("/WEB-INF/views/admin/seeOneExpo.jsp");
        }
        return Route.setFullRoutePath("redirect:/views/admin/admin.jsp", Route.RouteType.REDIRECT);
    }
}
