package com.myproject.expo.expositions.command.general;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.util.CommandUtil;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogOutCommand.class);

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) {
        String userEmail = (String) req.getSession().getAttribute(Constant.USER_EMAIL);
        CommandUtil.getLoggedUsers().remove(userEmail);
        CommandUtil.setRoleForUser(null,null,req);
        req.getSession().setAttribute(Constant.USER_DATA,null);
        clearSessionFromAllLists(req);
        logger.info(userEmail + " has just logout");
        return Route.setFullRoutePath("/expo/", Route.RouteType.REDIRECT);
    }

    private void clearSessionFromAllLists(HttpServletRequest req){
        HttpSession session = req.getSession();;
        session.setAttribute(Constant.HALL_LIST,null);
        session.setAttribute(Constant.THEME_LIST,null);
        session.setAttribute(Constant.EXPOS_LIST,null);
        session.setAttribute(Constant.SEARCHED_LIST,null);
    }
}
