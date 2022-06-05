package com.myproject.expo.expositions.controller.filter;

import com.myproject.expo.expositions.command.general.LogOutCommand;
import com.myproject.expo.expositions.controller.util.PathPage;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class AuthFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AuthFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        System.out.println("URI " + req.getRequestURI());
        System.out.println("URL " + req.getRequestURL());
        System.out.println("Servlet " + req.getServletPath());


        redirectRequestToControllerIfRequired(req, resp);
        if (logoutInCaseBackArrowPressed(req)) {
            resp.sendRedirect(new LogOutCommand().execute(req, resp).getPathOfThePage());
        }

        if (caseAfterLogoutPressedBackArrowAgain(req)) {
            resp.sendRedirect(Constant.URL.ROOT_FULL_PATH);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void redirectRequestToControllerIfRequired(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getParameter(Constant.COMMAND) != null) {
            req.getRequestDispatcher(Constant.URL.CONTROLLER).forward(req, resp);
        }
    }

    private boolean checkIfURIAndRoleNotNull(String URI, String role) {
        return URI != null && role != null;
    }

    private boolean checkURLAndRoleContainsRequiredPathAndRole(String URI, String role) {
        return URI.contains(Constant.URL.LOGIN) || URI.contains(Constant.URL.REGISTER) && role != null;
    }

    private boolean logoutInCaseBackArrowPressed(HttpServletRequest req) {
        String URI = req.getRequestURI();
        String role = (String) req.getSession().getAttribute(Constant.ROLE);
        return checkURLAndRoleContainsRequiredPathAndRole(URI, role) && checkIfURIAndRoleNotNull(URI, role);
    }

    private boolean caseAfterLogoutPressedBackArrowAgain(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute(Constant.USER_DATA);
        return user == null && Arrays.stream(PathPage.values())
                .anyMatch(val -> req.getRequestURI().equals(val.getURI()));
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
