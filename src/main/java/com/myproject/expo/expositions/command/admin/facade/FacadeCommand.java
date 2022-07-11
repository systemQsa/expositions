package com.myproject.expo.expositions.command.admin.facade;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.Service;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The FacadeCommand class get`s the desired command related to crud operations with hall entity,theme entity,redirects
 * command to required class where processing the request
 * @param <T>
 */
public class FacadeCommand<T> implements Command {
    private static final Logger logger = LogManager.getLogger(FacadeCommand.class);
    private final Command command;
    private final Service<T> service;

    public FacadeCommand(Command command, Service<T> service) {
        this.command = command;
        this.service = service;
    }

    @Override
    public Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        HttpSession session = req.getSession();
        long noOfRecords = (long) session.getAttribute(Constant.NO_OF_RECORDS);
        String sortBy = req.getParameter(Constant.SORT_BY);
        try {
            Route route = command.execute(req, resp);
            service.setListOfFoundedRecordsToTheSession(session,service.getAllRecords(1, noOfRecords,sortBy));
            service.setCurrPageAndNoOfRecordsToSession(session,1,noOfRecords);
            return route;
        } catch (ServiceException | CommandException e) {
            logger.warn("By some reason failed FacadeCommand Class" + e.getMessage());
            throw new CommandException(Constant.URL.FULL_ADMIN_PAGE);
        }
    }
}
