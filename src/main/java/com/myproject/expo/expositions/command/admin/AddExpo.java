package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.factory.ServiceFactory;
import com.myproject.expo.expositions.factory.impl.ServiceFactoryImpl;
import com.myproject.expo.expositions.service.ExpositionService;
import com.myproject.expo.expositions.util.Constant;
import com.myproject.expo.expositions.validation.Validate;
import com.myproject.expo.expositions.validation.ValidateInput;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddExpo implements Command {
    private static final Logger logger = LogManager.getLogger(AddExpo.class);
    private final Validate validate;
    private final ExpositionService<Exposition> expoService;
    private final ServiceFactory serviceFactory;

    public AddExpo() {
        validate = new ValidateInput();
        serviceFactory = new ServiceFactoryImpl();
        expoService = serviceFactory.getExpoService();
    }

    public AddExpo(Validate validate, ServiceFactory serviceFactory, ExpositionService<Exposition> expoService) {
        this.validate = validate;
        this.serviceFactory = serviceFactory;
        this.expoService = expoService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            addingExpoProcess(req);
            return Route.setFullRoutePath(Constant.URL.ADMIN_REDIRECT, Route.RouteType.REDIRECT);
        } catch (ValidationException | ServiceException e) {
            setInformMessageToUser(15, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
    }

    private void addingExpoProcess(HttpServletRequest req) throws ValidationException, ServiceException {
        List<Exposition> hallList = (List<Exposition>) req.getSession().getAttribute(Constant.EXPOS_LIST);
        Exposition addedExpo = buildExpositionFromReq(req, validate);
        informHallIsBusy(hallList, addedExpo);
        expoService.add(addedExpo);
        clearSessionFromAllLists(req);
    }

    private Exposition buildExpositionFromReq(HttpServletRequest req, Validate validate) throws ValidationException {
        return Exposition.builder()
                .setExpoName(req.getParameter("expoName"))
                .setExpoDate(parseStrToLocalDate(validate.dateValidate(req.getParameter("expoDate"))))
                .setExpoTime(parseStrToLocalTime(validate.timeValidate(req.getParameter("expoTime"))))
                .setExpoPrice(parseToBigDecimal(validate.priceValidate(req.getParameter("expoPrice"))))
                .setExpoSoldTickets(parseStrToLong(validate.onlyDigitsValidate(req.getParameter("expoSold"))))
                .setHallList(buildHallList(req))
                .setTheme(buildTheme(req))
                .setTickets(parseStrToLong(validate.onlyDigitsValidate(req.getParameter("expoTickets")))).build();
    }

    private List<Hall> buildHallList(HttpServletRequest req) {
        return Arrays.stream(req.getParameterValues("halls"))
                .map(this::parseStrToLong)
                .map(value -> Hall.builder().setIDHall(value).build())
                .collect(Collectors.toList());
    }

    private Theme buildTheme(HttpServletRequest req) {
        return Theme.builder().setIDTheme(parseStrToLong(req.getParameter("idTheme"))).build();
    }

    private void informHallIsBusy(List<Exposition> hallList, Exposition addedExpo) throws ValidationException {
        if (isHallBusyAtTheTimeAndDate(hallList, addedExpo)) {
            throw new ValidationException("err.hall_busy");
        }
    }

    private boolean isHallBusyAtTheTimeAndDate(List<Exposition> list, Exposition expo) {
        return list.stream()
                .filter(e -> e.getTime().compareTo(expo.getTime()) == 0)
                .filter(e -> e.getHallList().stream()
                        .anyMatch(elem -> expo.getHallList().stream()
                                .anyMatch(val -> val.getIdHall() == elem.getIdHall())))
                .anyMatch(exposition -> expo.getDate().compareTo(exposition.getDate()) == 0);
    }

    private void clearSessionFromAllLists(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute(Constant.HALL_LIST, null);
        session.setAttribute(Constant.THEME_LIST, null);
        session.setAttribute(Constant.EXPOS_LIST, null);
    }
}
