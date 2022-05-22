package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.UtilMethods;
import com.myproject.expo.expositions.command.util.DefinePathForUser;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.ExpositionService;
import com.myproject.expo.expositions.util.Constant;
import com.myproject.expo.expositions.validation.Validate;
import com.myproject.expo.expositions.validation.ValidateInput;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class UpdateExpo implements Command, UtilMethods {
    private static final Logger logger = LogManager.getLogger(UpdateExpo.class);
    private final ExpositionService<Exposition> expoService;
    private Validate validate;

    public UpdateExpo() {
        expoService = new AbstractFactoryImpl().getServiceFactory().getExpoService();
        validate = new ValidateInput();
    }

    public UpdateExpo(ExpositionService<Exposition> expoService) {
        this.expoService = expoService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        User user = (User) req.getSession().getAttribute(Constant.USER_DATA);
        try {
            Exposition expo = buildExpositionFromReq(req, validate);
             expoService.update(expo);
             req.getSession().setAttribute(Constant.HALL_LIST,null);
            return Route.setFullRoutePath(Constant.REDIRECT + DefinePathForUser.definePath(user.getUserRole().getRole()),
                    Route.RouteType.REDIRECT);
        } catch (ValidationException | ServiceException e) {
            logger.warn("Updating the exposition has failed");
            setInformMessageToUser(13, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
    }

    private Exposition buildExpositionFromReq(HttpServletRequest req, Validate validate) throws ValidationException {
        return Exposition.builder()
                .setExpositionID(parseStrToLong(req.getParameter(Constant.Param.EXPO_ID)))
                .setExpoName(req.getParameter(Constant.Param.EXPO_NAME))
                .setExpoDate(parseStrToLocalDate(validate.dateValidate(req.getParameter(Constant.Param.EXPO_DATE))))
                .setExpoTime(parseStrToLocalTime(validate.timeValidate(req.getParameter(Constant.Param.EXPO_TIME))))
                .setExpoPrice(parseToBigDecimal(validate.priceValidate(req.getParameter(Constant.Param.EXPO_PRICE))))
                .setExpoSoldTickets(parseStrToLong(validate.onlyDigitsValidate(req.getParameter(Constant.Param.EXPO_SOLD))))
                .setTheme(buildTheme(req))
                .setHallList(buildHallList(req))
                .setTickets(parseStrToLong(validate.onlyDigitsValidate(req.getParameter(Constant.Param.EXPO_TICKETS)))).build();
    }


    private List<Hall> buildHallList(HttpServletRequest req) {
        return Arrays.stream(req.getParameterValues(Constant.Param.EXPO_HALL_ID))
                .map(val -> Hall.builder().setIDHall(parseStrToLong(val)).build())
                .collect(Collectors.toList());
    }

    private Theme buildTheme(HttpServletRequest req) {
        List<Theme> list = (List<Theme>) req.getSession().getAttribute(Constant.THEME_LIST);
       return list.stream()
                .filter(l -> l.getIdTheme() == parseStrToLong(req.getParameter(Constant.Param.ID_THEME)))
                .findFirst()
                .orElse(null);

    }
}
