package com.sagr.simple.chat.security;


import com.sagr.user.common.IUserService;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sasha on 24.05.14.
 */
public class CustomFilter implements Filter {

    private static final String FORM_USERNAME_KEY = "j_username";
    private static final String FILTER_PROCESSES_URL = "/j_spring_security_check";

    private String redirectUrl = "/welcome?alreadyRegistered=true";
    private RedirectStrategy strategy = new DefaultRedirectStrategy();

    @Resource(name = "userService")
    private IUserService userService;

    @Override
    public void destroy() {
        // Do nothing
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (check(request)) {
            String userName = request.getParameter(FORM_USERNAME_KEY);
            if (userName != null) {
                userName = userName.trim();
                if (userName.isEmpty()) {

                } else {
                    if (userService.userExists(userName)) {
                        strategy.sendRedirect(request, response, getFullUrl(request));
                        return;
                    } else {
                        userService.registerNewUser(userName);
                    }
                }
            }
        }
        chain.doFilter(req, res);

    }

    protected String getFullUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath + redirectUrl;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // Do nothing
    }

    protected boolean check(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            // strip everything after the first semi-colon
            uri = uri.substring(0, pathParamIndex);
        }

        if ("".equals(request.getContextPath())) {
            return uri.endsWith(FILTER_PROCESSES_URL);
        }

        return uri.endsWith(request.getContextPath() + FILTER_PROCESSES_URL);
    }

    public RedirectStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(RedirectStrategy strategy) {
        this.strategy = strategy;
    }
}