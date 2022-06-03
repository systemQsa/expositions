package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.factory.impl.AbstractFactoryImpl;
import com.myproject.expo.expositions.service.entity_iservice.HallService;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ViewAllHallsCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ViewAllHallsCommand.class);
    private final HallService hallService;

    public ViewAllHallsCommand() {
        hallService = new AbstractFactoryImpl().getServiceFactory().getHallService();
    }

    public ViewAllHallsCommand(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        long page = parseStrToLong(req.getParameter(Constant.PAGE));
        long noOfRecords = parseStrToLong(req.getParameter(Constant.NO_OF_RECORDS));
        String sortBy = req.getParameter(Constant.SORT_BY);
        HttpSession session = cleanSession(req);
        try {
            List<Hall> list = hallService.getAllRecords(page, noOfRecords,sortBy);
            list.forEach(System.out::println);
            hallService.setListOfFoundedRecordsToTheSession(session, list);
            hallService.setCurrPageAndNoOfRecordsToSession(session,page,noOfRecords);
        } catch (ServiceException e) {
            logger.warn("Cannot get List<Hall>. ViewAllHallsCommand class failed");
            setInformMessageToUser(7, req, e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
        return Route.setFullRoutePath(Constant.URL.FULL_ADMIN_PAGE, Route.RouteType.FORWARD);
    }

}
