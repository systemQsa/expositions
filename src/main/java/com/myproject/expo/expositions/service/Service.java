package com.myproject.expo.expositions.service;

import com.myproject.expo.expositions.dao.Removable;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface Service<T> extends GeneralService<T>, Removable {

    void setListOfFoundedRecordsToTheSession(HttpSession session, List<T> list, long currPage, long noOfRecords);
}
