package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.util.DefinePathForUser;
import com.myproject.expo.expositions.controller.util.PathPage;
import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.entity_iservice.ExpositionService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The ViewAllExposCommand class gets list of expositions and returns it to the desired endpoint
 */
public class ViewAllExposCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ViewAllExposCommand.class);
    private final ExpositionService<Exposition> expoService;

    public ViewAllExposCommand() {
        expoService = new AbstractFactoryImpl().getServiceFactory().getExpoService();
    }

    public ViewAllExposCommand(ExpositionService<Exposition> expoService) {
        this.expoService = expoService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        HttpSession session = cleanSession(req);
        User user = (User) session.getAttribute(Constant.USER_DATA);
        long page = parseStrToLong(req.getParameter(Constant.PAGE));
        long noOfRecords = setInitNoOfRecords(req);
        String sortBy = setInitSortBy(req);
        Route route = getRouteDependOnUserRole(user);
        try {
            gettingAllExposProcess(req, session, page, noOfRecords, sortBy);
            return route;
        } catch (ServiceException e) {
            logger.warn("ViewAllExposCommand has failed");
            setInformMessageToUser(12, req, e.getMessage());
            throw new CommandException(route.getPathOfThePage());
        }
    }

    private void gettingAllExposProcess(HttpServletRequest req, HttpSession session, long page, long noOfRecords, String sortBy) throws ServiceException {
        List<Exposition> list = expoService.getAllRecords(page, noOfRecords, sortBy);
        expoService.setListOfFoundedRecordsToTheSession(session, list);
        expoService.setCurrPageAndNoOfRecordsToSession(session, page, noOfRecords);
        session.setAttribute(Constant.SORT_BY, sortBy);
        req.setAttribute(Constant.LIST_HEADER, setHeaderForTableList(req, sortBy));
    }

    private String setHeaderForTableList(HttpServletRequest req, String sortBy) {
        if (sortBy.equals(Constant.STATISTIC)) {
            return translateInfoMessageToRequiredLang(Constant.STATISTICS, req);
        } else {
            return translateInfoMessageToRequiredLang(Constant.EXPOSITIONS, req);
        }
    }

    private Route getRouteDependOnUserRole(User user) {
        return Route.setFullRoutePath(checkUserHasRoleElseReturnPath(user), Route.RouteType.FORWARD);
    }

    private String checkUserHasRoleElseReturnPath(User user) {
        if (user == null) {
            return DefinePathForUser.definePath(Constant.GUEST);
        }
        return PathPage.getRequiredPagePath(DefinePathForUser.definePath(user.getUserRole().getRole()));
    }
}
