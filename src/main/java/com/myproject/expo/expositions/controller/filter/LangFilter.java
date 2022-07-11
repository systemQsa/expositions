package com.myproject.expo.expositions.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * The LangFilter filter switches the language
 */
public class LangFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LangFilter.class);
    private ResourceBundle bundle = ResourceBundle.getBundle(Constant.RESOURCES, Locale.ENGLISH);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        setInitialLanguage(request, session);
        setRequiredLanguage(request, session);

        logger.info(Constant.LogMsg.LANG_FILTER_WORK);
        filterChain.doFilter(request, servletResponse);
    }

    private void setInitialLanguage(HttpServletRequest request, HttpSession session) {
        if (session.getAttribute(Constant.LANG) == null && request.getParameter(Constant.LANG) == null) {
            setDesiredLangToSession(session, bundle, Constant.ENG, Constant.COUNTRY_US);
        }
    }

    private void setRequiredLanguage(HttpServletRequest request, HttpSession session) {
        Optional.ofNullable(request.getParameter(Constant.LANG))
                .ifPresent(lang -> bundle = ResourceBundle.getBundle(Constant.RESOURCES,
                        new Locale(lang, request.getParameter(Constant.COUNTRY))));
        setDesiredLangToSession(session, bundle, request.getParameter(Constant.LANG), request.getParameter(Constant.COUNTRY));
    }

    private void setDesiredLangToSession(HttpSession session, ResourceBundle bundle, String lang, String country) {
        session.setAttribute(Constant.LANG, lang);
        session.setAttribute(Constant.LANGUAGE, bundle);
        session.setAttribute(Constant.COUNTRY, country);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
