package com.myproject.expo.expositions.command;

import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Command extends UtilMethods {
    Route execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException;

    default String setInitSortBy(HttpServletRequest req) {
        return req.getParameter(Constant.SORT_BY) == null ? "id" : req.getParameter(Constant.SORT_BY);
    }

    default long setInitNoOfRecords(HttpServletRequest req) {
        return req.getParameter(Constant.NO_OF_RECORDS) == null ? 5 : parseStrToLong(req.getParameter(Constant.NO_OF_RECORDS));
    }

    default HttpSession cleanSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute(Constant.THEME_LIST, null);
        session.setAttribute(Constant.HALL_LIST, null);
        session.setAttribute(Constant.USER_EXPOS,null);
        session.setAttribute(Constant.EXPOS_LIST,null);
        session.setAttribute(Constant.SEARCHED_LIST,null);
        session.setAttribute(Constant.USERS_LIST,null);
        return session;
    }
}
