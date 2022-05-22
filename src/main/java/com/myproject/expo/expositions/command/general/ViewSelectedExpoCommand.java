package com.myproject.expo.expositions.command.general;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.util.DefinePathForUser;
import com.myproject.expo.expositions.controller.util.PathPage;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.ServiceFactory;
import com.myproject.expo.expositions.factory.impl.ServiceFactoryImpl;
import com.myproject.expo.expositions.service.HallService;
import com.myproject.expo.expositions.service.ThemeService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewSelectedExpoCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ViewSelectedExpoCommand.class);
    private final HallService hallService;
    private final ServiceFactory serviceFactory;
    private ThemeService themeService;

    public ViewSelectedExpoCommand() {
        serviceFactory = new ServiceFactoryImpl();
        hallService = serviceFactory.getHallService();
        themeService = serviceFactory.gerThemeService();
    }

    public ViewSelectedExpoCommand(ServiceFactory serviceFactory, HallService hallService) {
        this.serviceFactory = serviceFactory;
        this.hallService = hallService;

    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        User user = (User) req.getSession().getAttribute(Constant.USER_DATA);
        List<Exposition> list = (List<Exposition>) req.getSession().getAttribute(Constant.EXPOS_LIST);
        setSelectedExpoToSession(req, list);
        try {
            hallService.setListOfFoundedRecordsToTheSession(req.getSession(), hallService.getAllRecords(1, 2, Constant.ID), 1, 2);
            themeService.setListOfFoundedRecordsToTheSession(req.getSession(),themeService.getAllRecords(1,2,Constant.ID),1,2);
        } catch (ServiceException e) {
            setInformMessageToUser(20, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
        return Route.setFullRoutePath(PathPage.getRequiredPagePath(DefinePathForUser.definePath(user.getUserRole()
                        .getRole())).replaceAll(Constant.REGEX_FOR_PIECE_OF_PATH_TO_CHANGE, Constant.URL.SEE_ONE_EXPO_REPLACEMENT),
                Route.RouteType.FORWARD);
    }

    private void setSelectedExpoToSession(HttpServletRequest req, List<Exposition> list) {
        long expoId = parseStrToLong(req.getParameter(Constant.EXPO_ID));
        req.setAttribute(Constant.ONE_EXPO_DATA,
                list.stream()
                        .filter(expo -> expo.getIdExpo() == expoId)
                        .findFirst()
                        .orElse(null));
    }
}
