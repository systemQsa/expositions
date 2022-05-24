package com.myproject.expo.expositions.service;

import com.myproject.expo.expositions.dao.Removable;
import com.myproject.expo.expositions.util.Constant;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface Service<T> extends GeneralService<T>, Removable {

    void setListOfFoundedRecordsToTheSession(HttpSession session, List<T> list);

    default void setCurrPageAndNoOfRecordsToSession(HttpSession session, long currPage, long noOfRecords) {
        session.setAttribute(Constant.CURRENT_PAGE, currPage);
        session.setAttribute(Constant.NO_OF_RECORDS, noOfRecords);
    }
}
