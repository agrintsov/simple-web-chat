package com.sagr.simple.chat.controllers;

import com.sagr.user.common.IUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Sasha on 26.05.14.
 */
public abstract class ABasicController {

    protected String getLoggedInPersonName() {
        if (SecurityContextHolder.getContext().getAuthentication() == null
                || SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().equals("anonymousUser")) {
            return null;
        } else {
            Object loggedInObject = SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            if (loggedInObject instanceof IUser) {
                return ((IUser) loggedInObject).getName();
            } else {
                return null;
            }
        }
    }
}
