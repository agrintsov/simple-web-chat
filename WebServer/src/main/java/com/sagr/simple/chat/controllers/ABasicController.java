package com.sagr.simple.chat.controllers;

import com.sagr.user.common.IUser;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class ABasicController {

    protected IUser getLoggedInPerson() {
        if (SecurityContextHolder.getContext().getAuthentication() == null
                || SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().equals("anonymousUser")) {
            return null;
        } else {
            Object loggedInObject = SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            if (loggedInObject instanceof IUser) {
                return ((IUser) loggedInObject);
            } else {
                return null;
            }
        }
    }
}
