package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.util.DefinePathForUser;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.ThemeService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddThemeCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddThemeCommand.class);
    private final ThemeService themeService;

    public AddThemeCommand() {
        themeService = new AbstractFactoryImpl().getServiceFactory().gerThemeService();
    }

    public AddThemeCommand(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        User user = (User) req.getSession().getAttribute(Constant.USER_DATA);
        req.getSession().setAttribute(Constant.THEME_LIST,null);
        try {
            Theme theme = Theme.builder().setThemeName(req.getParameter(Constant.THEME_NAME)).build();
            themeService.add(theme);
            return Route.setFullRoutePath(Constant.REDIRECT + DefinePathForUser.definePath(user.getUserRole().getRole()),
                    Route.RouteType.REDIRECT);
        } catch (ServiceException e) {
            logger.warn("Cannot add a new Theme in AddThemeCommand class");
            setInformMessageToUser(5, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
    }
}
