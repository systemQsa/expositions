package com.myproject.expo.expositions.controller;

import com.myproject.expo.expositions.command.Route;
import com.myproject.expo.expositions.command.StorageCommand;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = Constant.CONTROLLER, urlPatterns = Constant.URL.CONTROLLER)
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("GET METHOD IN CONTROLLER WORKS");
        processTheRequest(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("POST METHOD IN CONTROLLER WORKS");
        processTheRequest(req, resp);
    }

    private void processTheRequest(HttpServletRequest req,
                                   HttpServletResponse resp) throws ServletException, IOException {
        try {
            sendResponseToTheClientForwardOrRedirect(req, resp, StorageCommand.getCommand(req.getParameter(Constant.ACTION)).execute(req, resp));
        } catch (CommandException e) {
            req.getRequestDispatcher(e.getMessage()).forward(req, resp);
            logger.warn(Constant.LogMsg.CONTROLLER_FAILED);
        }
    }

    private void sendResponseToTheClientForwardOrRedirect(HttpServletRequest req, HttpServletResponse resp,
                                                          Route route) throws ServletException, IOException {
        if (route.getRoute().equals(Route.RouteType.FORWARD)) {
            req.getRequestDispatcher(route.getPathOfThePage()).forward(req, resp);
        } else if (route.getPathOfThePage().contains(Constant.REDIRECT)) {
            resp.sendRedirect(route.getPathOfThePage().replaceAll(Constant.REDIRECT, Constant.URL.ROOT));
        } else {
            resp.sendRedirect(route.getPathOfThePage());
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }
}
