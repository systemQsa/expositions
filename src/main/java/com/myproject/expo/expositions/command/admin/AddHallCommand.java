package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.util.DefinePathForUser;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.HallService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddHallCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddHallCommand.class);
    private final HallService hallService;

    public AddHallCommand() {
        hallService = new AbstractFactoryImpl().getServiceFactory().getHallService();
    }

    public AddHallCommand(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constant.USER_DATA);
        session.setAttribute(Constant.THEME_LIST,null);
        try {
            //todo turn on the method
            Hall hall = Hall.builder().setHallName(req.getParameter("hallName")).build();
            hallService.add(hall);
            return Route.setFullRoutePath(Constant.REDIRECT + DefinePathForUser.definePath(user.getUserRole().getRole()),
                    Route.RouteType.REDIRECT);
        } catch (ServiceException e) {
            setInformMessageToUser(8, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
    }
}
