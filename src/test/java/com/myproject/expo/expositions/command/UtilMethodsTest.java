package com.myproject.expo.expositions.command;

import com.myproject.expo.expositions.command.admin.AddThemeCommand;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.dao.entity.UserRole;
import com.myproject.expo.expositions.service.entity_iservice.ThemeService;
import com.myproject.expo.expositions.service.impl.ThemeServiceImpl;
import com.myproject.expo.expositions.validation.Validate;
import com.myproject.expo.expositions.validation.ValidateInput;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class UtilMethodsTest {
    private static HttpServletRequest req;
    private static Command command;
    private static HttpSession session;
    private static final User user = User.builder()
            .setUserRole(UserRole.ADMIN)
            .setEmail("admin@gmail.com")
            .build();

    private static final ResourceBundle dummyBundle = new ResourceBundle() {
        @Override
        protected Object handleGetObject(String key) {
            return "some string returned";
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.emptyEnumeration();
        }
    };

    @BeforeClass
    public static void init() {
        req = mock(HttpServletRequest.class);
        Validate validate = mock(ValidateInput.class);
        ThemeService themeService = mock(ThemeServiceImpl.class);
        command = new AddThemeCommand(themeService, validate);
        session = mock(HttpSession.class);
    }

    @Before
    public void before() {
        when(req.getSession()).thenReturn(session);
    }

    @Test
    public void getUserFromSession() {
        when((User) req.getSession().getAttribute(anyString())).thenReturn(user);
        Assertions.assertEquals("admin@gmail.com", command.getUserFromSession(req).getEmail());

    }

    @Test
    public void getNoOfRecordsFromSession() {
        when(req.getSession().getAttribute("noOfRecords")).thenReturn(5);
        Assertions.assertEquals(5, command.getNoOfRecordsFromSession(req));
    }

    @Test
    public void translateInfoMessageToRequiredLang() {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("language")).thenReturn(dummyBundle);
        Assertions.assertEquals("some string returned", command.translateInfoMessageToRequiredLang("String", req));
    }

    @Test
    public void parseStrToInt() {
        Assertions.assertEquals(123, command.parseStrToLong("123"));
    }
}