package com.myproject.expo.expositions.controller.filter;

import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CleanCacheFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(CleanCacheFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(Constant.CACHE_CONTROL, Constant.NO_STORE_MUST_REVALIDATE);
        response.setHeader(Constant.PRAGMA, Constant.NO_CACHE);
        response.setDateHeader(Constant.EXPIRES, 0);
        logger.info(Constant.LogMsg.CLEAN_CACHE_FILTER_WORKING);
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
