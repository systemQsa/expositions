package com.myproject.expo.expositions.command.general;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.util.DefinePathForUser;
import com.myproject.expo.expositions.controller.util.PathPage;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.ServiceFactory;
import com.myproject.expo.expositions.factory.impl.ServiceFactoryImpl;
import com.myproject.expo.expositions.service.ExpositionService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SearchExpo implements Command {
    private static final Logger logger = LogManager.getLogger(SearchExpo.class);
    private static final String REGEX_ONLY_WORDS = "^(\\p{L}+){3,}$";
    private static final String REGEX_DATE_ENG = "\\d{1,2}/\\d{2}/\\d{2}";
    private static final String REGEX_DATE_UKR = "\\d{1,2}\\.\\d{2}\\.\\d{2}";
    private final ExpositionService<Exposition> expoService;
    private final ServiceFactory serviceFactory;

    public SearchExpo() {
        serviceFactory = new ServiceFactoryImpl();
        expoService = serviceFactory.getExpoService();
    }

    public SearchExpo(ServiceFactory serviceFactory, ExpositionService<Exposition> expoService) {
        this.serviceFactory = serviceFactory;
        this.expoService = expoService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String searchBy = req.getParameter(Constant.INLINE_RADIO_BTN);
        String searchedItem = req.getParameter(Constant.SEARCHED_ITEM);
        User user = (User) req.getSession().getAttribute(Constant.USER_DATA);
        try {
            checkInputNotEmpty(searchBy, searchedItem);
            putResToSessionIfPresent(req, getSearchedExposByTheme(searchBy, searchedItem));
            putResToSessionIfPresent(req, getSearchedExposByDates(searchBy, searchedItem));
            return Route.setFullRoutePath(defineRoute(user), Route.RouteType.FORWARD);
        } catch (ServiceException e) {
            logger.info("Searching the item" + searchedItem + " was failed");
            setInformMessageToUser(10, req, e.getMessage());
            throw new CommandException(defineRoute(user));
        }
    }

    private void checkInputNotEmpty(String searchBy, String searchedItem) throws ServiceException {
        if (searchBy == null || searchedItem == null) {
            throw new ServiceException("err.incorrect_search");
        }
    }

    private void putResToSessionIfPresent(HttpServletRequest req, List<Exposition> list) throws ServiceException {
        Optional.ofNullable(list)
                .ifPresent(res -> req.getSession().setAttribute(Constant.SEARCHED_LIST, list));

    }

    private List<Exposition> getSearchedExposByTheme(String searchBy, String searchedItem) throws ServiceException {
        return Pattern.compile(REGEX_ONLY_WORDS).matcher(searchedItem).matches()
                ? expoService.searchExpo(searchBy, searchedItem) : null;
    }

    private List<Exposition> getSearchedExposByDates(String searchBy, String searchedItem) throws ServiceException {
        return (Pattern.compile(REGEX_DATE_ENG).matcher(searchedItem).matches() || Pattern.compile(REGEX_DATE_UKR).matcher(searchedItem).matches())
                ? expoService.searchExpo(searchBy, parseStrToLocalDate(searchedItem))
                : null;
    }

    private String defineRoute(User user) {
        if (user == null) {
            return DefinePathForUser.definePath(Constant.GUEST);
        }
        return PathPage.getRequiredPagePath(DefinePathForUser.definePath(user.getUserRole().getRole()));
    }
}
