package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.entity.UserRole;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.entity_iservice.HallService;
import com.myproject.expo.expositions.service.impl.HallServiceImpl;
import com.myproject.expo.expositions.util.Constant;
import com.myproject.expo.expositions.validation.Validate;
import com.myproject.expo.expositions.validation.ValidateInput;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddHallCommandTest {
    private static Command command;
    private static HallService hallService;
    private static HttpServletRequest req;
    private static HttpServletResponse resp;
    private static HttpSession session;
    private static final List<Hall> hallList = Collections.singletonList(Hall.builder().setHallName("HallName").build());
    private static final User user = User.builder().setUserRole(UserRole.ADMIN).build();

    @BeforeClass
    public static void init() {
        hallService = mock(HallServiceImpl.class);
        Validate validate = mock(ValidateInput.class);
        command = new AddHallCommand(hallService, validate);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void execute() throws ServiceException, CommandException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(Constant.USER_DATA)).thenReturn(user);
        when(req.getParameter(Constant.NO_OF_RECORDS)).thenReturn("1");
        when(hallService.getAllRecords(1, 3,"")).thenReturn(hallList);

        Assertions.assertEquals("redirect:/views/admin/admin.jsp",command.execute(req, resp).getPathOfThePage());
    }
}