package com.sagr.simple.chat.security;

import com.sagr.user.common.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.session.SessionRegistryImpl;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sasha on 24.05.14.
 */
public class SessionRegistryService {

    @Autowired
    private ApplicationContext appContext;

    @Resource(name="sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

    public Map<String, IUser> getAllLoggedInUsers(){
        List<Object> principals = sessionRegistry.getAllPrincipals();
        HashMap<String, IUser> users = new HashMap<String, IUser>();
        for (Object principal: principals) {
            if (principal instanceof IUser) {
                IUser user = (IUser) principal;
                users.put(user.getName(), user);
            }
        }
        return users;
    }

    public SessionRegistryImpl  getSessionRegistry() {
        return sessionRegistry;
    }

    public void setSessionRegistry(SessionRegistryImpl  sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }
}
