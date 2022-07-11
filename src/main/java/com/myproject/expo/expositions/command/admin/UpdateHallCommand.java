package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.util.DefinePathForUser;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.entity_iservice.HallService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The UpdateHallCommand class updates the required hall
 */
public class UpdateHallCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateHallCommand.class);
    private final HallService hallService;

    public UpdateHallCommand() {
        hallService = new AbstractFactoryImpl().getServiceFactory().getHallService();
    }

    public UpdateHallCommand(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        User user = (User) req.getSession().getAttribute(Constant.USER_DATA);
        req.getSession().setAttribute(Constant.HALL_LIST,null);
        try {
            Hall hall = Hall.builder().setIDHall(parseStrToLong(req.getParameter(Constant.ID_HALL)))
                    .setHallName(req.getParameter(Constant.HALL_NEW_NAME)).build();
            hallService.update(hall);
            return Route.setFullRoutePath(Constant.REDIRECT + DefinePathForUser.definePath(user.getUserRole().getRole()),
                    Route.RouteType.REDIRECT);
        } catch (ServiceException e) {
            setInformMessageToUser(9,req,e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
    }
}
