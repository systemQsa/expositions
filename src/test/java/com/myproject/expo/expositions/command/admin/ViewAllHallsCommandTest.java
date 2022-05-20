package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.HallService;
import com.myproject.expo.expositions.service.impl.HallServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ViewAllHallsCommandTest {
    private static HttpServletRequest req;
    private static HttpServletResponse resp;
    private static Command command;
    private static HttpSession session;
    private static HallService hallService;
    private static final List<Hall> hallList = Collections.singletonList(Hall.builder().setHallName("QWe").build());

    @BeforeClass
    public static void init() {
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        hallService = mock(HallServiceImpl.class);
        command = new ViewAllHallsCommand(hallService);

    }

    @Test
    public void execute() throws ServiceException, CommandException {
        when(req.getParameter(anyString())).thenReturn("1");
        when(req.getSession()).thenReturn(session);
        when(hallService.getAllRecords(1,2,"")).thenReturn(hallList);

        Assertions.assertEquals("/WEB-INF/views/admin/admin.jsp",command.execute(req,resp).getPathOfThePage());
    }
}