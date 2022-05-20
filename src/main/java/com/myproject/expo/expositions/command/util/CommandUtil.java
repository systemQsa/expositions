package com.myproject.expo.expositions.command.util;

import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandUtil {
    private static final Map<String, String> loggedUsers = new ConcurrentHashMap<>();

    public static void setRoleForUser(String email, String role, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute(Constant.USER_EMAIL, email);
        session.setAttribute(Constant.ROLE, role);
    }

    public static boolean userIsAlreadyLogged(HttpServletRequest req) throws ValidationException {
        if (loggedUsers.keySet().stream().noneMatch(key -> req.getParameter(Constant.EMAIL).equals(key))) {
            return false;
        }
          throw new ValidationException(Constant.ErrMsg.USER_IS_LOGGED);
    }

    public static Map<String, String> getLoggedUsers() {
        return loggedUsers;
    }
}
