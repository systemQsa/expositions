package com.myproject.expo.expositions.command.general;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.util.DefinePathForUser;
import com.myproject.expo.expositions.controller.util.PathPage;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.AllThemeHallService;
import com.myproject.expo.expositions.service.impl.AllThemeHallServiceImpl;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * The ViewSelectedExpoCommand class gets all information about separate exposition and returns expo to the required endpoint
 */
public class ViewSelectedExpoCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ViewSelectedExpoCommand.class);
    private final AllThemeHallService allThemeHallService;

    public ViewSelectedExpoCommand() {
        allThemeHallService = new AllThemeHallServiceImpl();
    }

    public ViewSelectedExpoCommand(AllThemeHallService allThemeHallService) {
        this.allThemeHallService = allThemeHallService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        User user = (User) req.getSession().getAttribute(Constant.USER_DATA);
        List<Exposition> list = (List<Exposition>) req.getSession().getAttribute(Constant.WHOLE_EXPO_LIST);
        setSelectedExpoToSession(req, list);
        try {
            req.getSession().setAttribute(Constant.HALL_LIST, allThemeHallService.allHalls());
            req.getSession().setAttribute(Constant.THEME_LIST, allThemeHallService.allThemes());
        } catch (ServiceException e) {
            setInformMessageToUser(20, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
        return getRouteBack(user);
    }

    private void setSelectedExpoToSession(HttpServletRequest req, List<Exposition> list) {
        long expoId = parseStrToLong(req.getParameter(Constant.EXPO_ID));
        req.setAttribute(Constant.ONE_EXPO_DATA,
                list.stream()
                        .filter(expo -> expo.getIdExpo() == expoId)
                        .findFirst()
                        .orElse(null));
    }

    private Route getRouteBack(User user) {
        return Optional.ofNullable(user)
                .filter(person -> person.getUserRole().getRole() != null)
                .map(person -> Route.setFullRoutePath(PathPage.getRequiredPagePath(DefinePathForUser.definePath(person.getUserRole()
                                .getRole())).replaceAll(Constant.REGEX_FOR_PIECE_OF_PATH_TO_CHANGE, Constant.URL.SEE_ONE_EXPO_REPLACEMENT),
                        Route.RouteType.FORWARD))
                .orElseGet(() -> Route.setFullRoutePath(Constant.URL.SEE_ONE_EXPO_FULL_PATH_USER, Route.RouteType.FORWARD));
    }
}
