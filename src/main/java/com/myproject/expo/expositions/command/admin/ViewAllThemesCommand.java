package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.entity_iservice.ThemeService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The ViewAllThemesCommand class gets list of theme and returns it to the admin page endpoint
 */
public class ViewAllThemesCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ViewAllThemesCommand.class);
    private final ThemeService themeService;

    public ViewAllThemesCommand() {
        themeService = new AbstractFactoryImpl().getServiceFactory().gerThemeService();
    }

    public ViewAllThemesCommand(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        long page = parseStrToLong(req.getParameter(Constant.PAGE));
        long noOfRecords = parseStrToLong(req.getParameter(Constant.NO_OF_RECORDS));
        String sortBy = req.getParameter(Constant.SORT_BY);
        HttpSession session = cleanSession(req);
        try {
            List<Theme> list = themeService.getAllRecords(page, noOfRecords, sortBy);
            themeService.setListOfFoundedRecordsToTheSession(session, list);
            themeService.setCurrPageAndNoOfRecordsToSession(session,page,noOfRecords);
        } catch (ServiceException e) {
            createInformMessage(req, page, noOfRecords, e);
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
        return Route.setFullRoutePath(Constant.URL.FULL_ADMIN_PAGE, Route.RouteType.FORWARD);
    }

    private void createInformMessage(HttpServletRequest req, long page, long noOrRecords, ServiceException e) {
        req.setAttribute(Constant.CURRENT_PAGE, page - 1);
        req.getSession().setAttribute(Constant.NO_OF_RECORDS, noOrRecords);
        setInformMessageToUser(4, req, e.getMessage());
        logger.warn("Cant get List<Theme>. Failed ViewThemesCommand class");
    }
}
