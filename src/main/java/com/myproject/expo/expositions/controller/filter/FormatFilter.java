package com.myproject.expo.expositions.controller.filter;

import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class FormatFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(FormatFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute(Constant.LANGUAGE);
       // System.out.println("bundle " + bundle.getLocale());

        session.setAttribute(Constant.DATE_FORMAT, setDateFormat(bundle.getLocale()));
        session.setAttribute(Constant.TIME_FORMAT, setTimeFormat(bundle.getLocale()));

        logger.info("FormatFilter working now");
        filterChain.doFilter(req, resp);
    }

    private DateTimeFormatter setDateFormat(Locale locale) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(locale);
    }

    private DateTimeFormatter setTimeFormat(Locale locale) {
        return DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                .withLocale(locale);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }
}
