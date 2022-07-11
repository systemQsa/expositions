package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.util.DefinePathForUser;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.entity_iservice.HallService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The DeleteHallCommand class deletes the hall
 */
public class DeleteHallCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteHallCommand.class);
    private final HallService hallService;

    public DeleteHallCommand() {
        hallService = new AbstractFactoryImpl().getServiceFactory().getHallService();
    }

    public DeleteHallCommand(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        User user = (User) req.getSession().getAttribute(Constant.USER_DATA);
        req.getSession().setAttribute(Constant.HALL_LIST,null);
        try {
            hallService.remove(parseStrToLong(req.getParameter(Constant.ID_HALL)));
            return Route.setFullRoutePath(Constant.REDIRECT + DefinePathForUser.definePath(user.getUserRole().getRole()),
                    Route.RouteType.REDIRECT);
        } catch (Exception e) {
            setInformMessageToUser(10,req,e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
    }
}
