package com.myproject.expo.expositions.command;

import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
