package com.myproject.expo.expositions.command.general;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.factory.ServiceFactory;
import com.myproject.expo.expositions.factory.impl.ServiceFactoryImpl;
import com.myproject.expo.expositions.service.entity_iservice.UserService;
import com.myproject.expo.expositions.service.encrypt.PassEncrypt;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * The ChangePassCommand class changes the old user password to the new one
 */
public class ChangePassCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangePassCommand.class);
    private final UserService userService;
    private final ServiceFactory serviceFactory;
    private final PassEncrypt passEncrypt;

    public ChangePassCommand() {
        serviceFactory = new ServiceFactoryImpl();
        userService = serviceFactory.getUserService();
        passEncrypt = new PassEncrypt();
    }

    public ChangePassCommand(ServiceFactory serviceFactory, UserService userService, PassEncrypt passEncrypt) {
        this.serviceFactory = serviceFactory;
        this.userService = userService;
        this.passEncrypt = passEncrypt;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String email = req.getParameter(Constant.EMAIL);
        try {
            String encrypt = passEncrypt.encrypt(String.valueOf(req.getParameter(Constant.NEW_PASS)).getBytes(StandardCharsets.UTF_8),
                    Constant.KEY);
            userService.changePass(email,encrypt.toCharArray());
        } catch (ServiceException | ValidationException e) {
            setInformMessageToUser(18,req,e.getMessage());
            throw new CommandException(Constant.URL.ROOT_FULL_PATH);
        }
        return Route.setFullRoutePath(Constant.URL.ROOT_FULL_PATH, Route.RouteType.REDIRECT);
    }
}
