package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.AllThemeHallService;
import com.myproject.expo.expositions.service.impl.AllThemeHallServiceImpl;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The PrepareToAddExpoCommand class prepares all required data for the page to add
 * to select in advance all desired options when adding the new exposition
 */
public class PrepareToAddExpoCommand implements Command {
    private static final Logger logger = LogManager.getLogger(PrepareToAddExpoCommand.class);
    private final AllThemeHallService allThemeHallService;

    public PrepareToAddExpoCommand() {
        allThemeHallService = new AllThemeHallServiceImpl();
    }

    public PrepareToAddExpoCommand(AllThemeHallService allThemeHallService) {
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
