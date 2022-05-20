package com.myproject.expo.expositions.command.general;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.util.CommandUtil;
import com.myproject.expo.expositions.command.util.DefinePathForUser;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.service.UserService;
import com.myproject.expo.expositions.service.impl.UserServiceImpl;
import com.myproject.expo.expositions.util.Constant;
import com.myproject.expo.expositions.validation.Validate;
import com.myproject.expo.expositions.validation.ValidateInput;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private final Validate validate = new ValidateInput();
    private final UserService userService;

    public LoginCommand() {
        userService = new UserServiceImpl();
    }

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {

        System.out.println(" email " + req.getParameter(Constant.EMAIL));
        try {
            User user = authenticationAndAuthorization(req);
            setUserToTheSession(req, user);
            cleanSessionFromAllLists(req);
            return Route.setFullRoutePath(Constant.REDIRECT + DefinePathForUser
                    .definePath(user.getUserRole().getRole()), Route.RouteType.REDIRECT);
        } catch (ValidationException | ServiceException e) {
            setInformMessageToUser(2, req, e.getMessage());
            logger.warn(req.getParameter(Constant.EMAIL) + Constant.LogMsg.USER_CANNOT_LOGIN);
            throw new CommandException(Constant.URL.LOGIN);
        }
    }

    private void setUserToTheSession(HttpServletRequest req, User user) {
        req.getSession().setAttribute(Constant.USER_DATA, user);
    }

    private User authenticationAndAuthorization(HttpServletRequest req) throws ValidationException, ServiceException {
        isInputCorrect(req);
        CommandUtil.userIsAlreadyLogged(req);
        User user = getUserFromService(req);
        CommandUtil.setRoleForUser(user.getEmail(), user.getUserRole().getRole(), req);
        CommandUtil.getLoggedUsers().put(user.getEmail(), user.getUserRole().getRole());
        return user;
    }

    private User getUserFromService(HttpServletRequest req) throws ServiceException {
        return userService.getUserByEmailAndPass(req.getParameter(Constant.EMAIL),
                req.getParameter(Constant.PASSWORD).toCharArray());
    }

    private boolean isInputCorrect(HttpServletRequest req) throws ValidationException {
        return validate.isEmailValid(req.getParameter(Constant.EMAIL))
                && validate.isPasswordValid(req.getParameter(Constant.PASSWORD));
    }

    private HttpSession cleanSessionFromAllLists(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute(Constant.THEME_LIST, null);
        session.setAttribute(Constant.EXPOS_LIST, null);
        session.setAttribute(Constant.HALL_LIST, null);
        return session;
    }
}
