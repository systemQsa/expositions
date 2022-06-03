package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.util.DefinePathForUser;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.entity_iservice.HallService;
import com.myproject.expo.expositions.util.Constant;
import com.myproject.expo.expositions.validation.Validate;
import com.myproject.expo.expositions.validation.ValidateInput;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

public class AddHallCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddHallCommand.class);
    private final HallService hallService;
    private final Validate validate;

    public AddHallCommand() {
        hallService = new AbstractFactoryImpl().getServiceFactory().getHallService();
        validate = new ValidateInput();
    }

    public AddHallCommand(HallService hallService,Validate validate) {
        this.hallService = hallService;
        this.validate = validate;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constant.USER_DATA);
        session.setAttribute(Constant.THEME_LIST,null);
        try {
            hallService.add(buildHallFromReq(req));
            return Route.setFullRoutePath(Constant.REDIRECT + DefinePathForUser.definePath(user.getUserRole().getRole()),
                    Route.RouteType.REDIRECT);
        } catch (ServiceException | ValidationException e) {
            setInformMessageToUser(8, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
    }

    private Hall buildHallFromReq(HttpServletRequest req) throws ValidationException {
        return Hall.builder()
                .setHallName(validate.notEmptyStr(req,Constant.Param.HALL_NAME))
                .build();
    }
}
