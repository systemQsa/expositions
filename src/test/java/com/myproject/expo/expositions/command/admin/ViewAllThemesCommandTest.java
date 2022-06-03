package com.myproject.expo.expositions.command.admin;

import com.myproject.expo.expositions.command.Command;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.exception.CommandException;
import com.myproject.expo.expositions.exception.ServiceException;
import com.myproject.expo.expositions.service.entity_iservice.ThemeService;
import com.myproject.expo.expositions.service.impl.ThemeServiceImpl;
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

public class ViewAllThemesCommandTest {
    private static HttpServletRequest req;
    private static HttpServletResponse resp;
    private static Command command;
    private static HttpSession session;
    private static ThemeService themeService;
    private static final List<Theme> themeList = Collections.singletonList(Theme.builder().setThemeName("science").build());

    @BeforeClass
    public static void init() {
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        themeService = mock(ThemeServiceImpl.class);
        command = new ViewAllThemesCommand(themeService);

    }

    @Test
    public void execute() throws ServiceException, CommandException {
        when(req.getParameter(anyString())).thenReturn("1");
        when(req.getSession()).thenReturn(session);
        when(themeService.getAllRecords(1,2,"")).thenReturn(themeList);

        Assertions.assertEquals("/WEB-INF/views/admin/admin.jsp",command.execute(req, resp).getPathOfThePage());

    }
}