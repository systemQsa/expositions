package com.myproject.expo.expositions.command.general;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.service.entity_iservice.UserService;
import com.myproject.expo.expositions.service.encrypt.PassEncrypt;
import com.myproject.expo.expositions.service.impl.UserServiceImpl;
import com.myproject.expo.expositions.util.Constant;
import com.myproject.expo.expositions.validation.Validate;
import com.myproject.expo.expositions.validation.ValidateInput;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * The RegisterCommand class register the new user in the system
 */
public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
    private final Validate validate = new ValidateInput();
    private final UserService userService;
    private final PassEncrypt passEncrypt;

    public RegisterCommand() {
        userService = new UserServiceImpl();
        passEncrypt = new PassEncrypt();
    }

    public RegisterCommand(UserService userService, PassEncrypt passEncrypt) {
        this.userService = userService;
        this.passEncrypt = passEncrypt;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            User user = retrieveUserDataFromReqToRegister(req);
            isUserInputCorrect(user);
            userService.registerUser(user);
        } catch (ValidationException | ServiceException e) {
            logger.warn(Constant.LogMsg.USER_ENTERED_INCORRECT_INPUT);
            setInformMessageToUser(1, req, e.getMessage());
            throw new CommandException(Constant.URL.REGISTER);
        }
        return Route.setFullRoutePath(Constant.URL.FULL_PATH_LOGIN, Route.RouteType.REDIRECT);
    }

    private User retrieveUserDataFromReqToRegister(HttpServletRequest req) throws ValidationException {
        User.UserBuilder user = User.builder();
        String encrypt = passEncrypt.encrypt(String.valueOf(req.getParameter(Constant.PASSWORD)).getBytes(StandardCharsets.UTF_8), Constant.KEY);
        return Optional.of(req)
                .map(request -> user.setName(request.getParameter(Constant.NAME))
                        .setSurname(request.getParameter(Constant.SURNAME))
                        .setEmail(request.getParameter(Constant.EMAIL))
                        .setPassword(encrypt.toCharArray())
                        .setPhone(request.getParameter(Constant.PHONE)))
                .get().build();
    }

    private boolean isUserInputCorrect(User user) throws ValidationException {
        return validate.isNameValid(user.getName())
                && validate.isSurnameValid(user.getSurname())
                && validate.isEmailValid(user.getEmail())
                && validate.isPhoneValid(user.getPhone());
    }
}
