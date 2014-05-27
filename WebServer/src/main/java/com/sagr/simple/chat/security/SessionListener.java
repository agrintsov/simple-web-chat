package com.sagr.simple.chat.security;

import com.sagr.user.common.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


@Component
public class SessionListener implements HttpSessionListener {

    private static final String SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    final Logger logger = LoggerFactory.getLogger(SessionListener.class);

    private IUserService userService;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        if (userService == null) {
            obtainDAO(event);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        Object context = session.getAttribute(SECURITY_CONTEXT);
        if (context != null) {
            SecurityContext securityContext = (SecurityContext)context;
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal != null) {
                    UserDetails user = (UserDetails) principal;
                    String username = user.getUsername();
                    if (username != null) {
                        userService.removeUser(username);
                        logger.info("User {} logged out", username);
                    }
                }
            }
        }
    }

    private void obtainDAO(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        userService = (IUserService) ctx.getBean("userService");
    }
}