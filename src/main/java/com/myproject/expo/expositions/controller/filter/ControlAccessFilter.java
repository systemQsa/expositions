package com.myproject.expo.expositions.controller.filter;

import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ControlAccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(ControlAccessFilter.class);
    private List<String> userAllowedUrls;
    private List<String> generalUrls;
    private List<String> adminAllowedUrls;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String role = (String) req.getSession().getAttribute(Constant.ROLE);

        if (isThereAnyDeniedUrls(req, role)) {
            req.getRequestDispatcher(Constant.URL.NOT_FOUND_PAGE).forward(req, resp);
            logger.info("User try to get denied page");
            return;
        }

        filterChain.doFilter(req, resp);
    }

    private boolean isThereAnyDeniedUrls(HttpServletRequest req, String role) {
        return checkUrlsForGuest(req, role) || checkUrlsForAdmin(req, role) || checkUrlsForUser(req, role);
    }

    private boolean checkUrlsForGuest(HttpServletRequest req, String role) {
        return role == null && isAllowedPathForUser(generalUrls, req);
    }

    private boolean checkUrlsForUser(HttpServletRequest req, String role) {
        return Objects.equals(role, Constant.USER) && isAllowedPathForUser(userAllowedUrls, req);
    }

    private boolean checkUrlsForAdmin(HttpServletRequest req, String role) {
        return (Objects.equals(role, Constant.ADMIN) && isAllowedPathForUser(adminAllowedUrls, req));
    }

    private boolean isAllowedPathForUser(List<String> list, HttpServletRequest req) {
        return list.stream()
                .noneMatch(url -> Objects.equals(url, req.getServletPath()));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        generalUrls = Arrays.asList("/index.jsp", "/login.jsp", "/register.jsp", "/changePass.jsp", "/changeEmail.jsp", "/controller");
        adminAllowedUrls = Arrays.asList("/views/admin/admin.jsp", "/views/admin/addNewExpo.jsp", "/controller", "/login.jsp", "/register.jsp");
        userAllowedUrls = Arrays.asList("/views/user/user.jsp", "/controller","/login.jsp","/register.jsp");
    }

    @Override
    public void destroy() {
        userAllowedUrls.clear();
        generalUrls.clear();
        adminAllowedUrls.clear();
    }
}
