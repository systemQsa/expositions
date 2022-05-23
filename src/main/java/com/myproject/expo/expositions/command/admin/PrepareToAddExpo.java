package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.AbstractFactory;
import com.myproject.expo.expositions.factory.ServiceFactory;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.AllThemeHallService;
import com.myproject.expo.expositions.service.HallService;
import com.myproject.expo.expositions.service.ThemeService;
import com.myproject.expo.expositions.service.impl.AllThemeHallServiceImpl;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PrepareToAddExpo implements Command {
    private static final Logger logger = LogManager.getLogger(PrepareToAddExpo.class);
    private final AllThemeHallService allThemeHallService;

    public PrepareToAddExpo() {
        allThemeHallService = new AllThemeHallServiceImpl();
    }

    public PrepareToAddExpo(AllThemeHallService allThemeHallService) {
        this.allThemeHallService = allThemeHallService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            req.getSession().setAttribute(Constant.HALL_LIST, allThemeHallService.allHalls());
            req.getSession().setAttribute(Constant.THEME_LIST, allThemeHallService.allThemes());
        } catch (ServiceException e) {
            logger.info("PrepareToAddExpo failed");
            setInformMessageToUser(14, req, e.getMessage());
            throw new CommandException(Constant.URL.ADD_NEW_EXPO_FULL_PATH);
        }
        return Route.setFullRoutePath(Constant.URL.ADD_EXPO_REDIRECT, Route.RouteType.REDIRECT);
    }
}
