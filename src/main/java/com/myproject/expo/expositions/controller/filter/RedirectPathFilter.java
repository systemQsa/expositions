package com.myproject.expo.expositions.controller.filter;

import com.myproject.expo.expositions.controller.util.PathPage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectPathFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(RedirectPathFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        switchPathIfRequired(req, resp);
        logger.info("RedirectPathFilter works");
        filterChain.doFilter(req, resp);
    }

    /**
     * Method checks if URI of the request contains required path if so redirects to the needed page
     *
     * @param req  http request
     * @param resp http response
     * @throws ServletException in case some problems occur
     * @throws IOException      in case some problems occur
     */
    private void switchPathIfRequired(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (PathPage.getRequiredPagePath(req) != null) {
            String path = PathPage.getRequiredPagePath(req);
            logger.info("Path : " + path);
            req.getRequestDispatcher(path).forward(req, resp);
            return;
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
