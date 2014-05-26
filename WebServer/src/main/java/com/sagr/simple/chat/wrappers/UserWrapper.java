package com.sagr.simple.chat.wrappers;

import com.sagr.user.common.IUser;

/**
 * Created by Sasha on 26.05.14.
 */
public class UserWrapper {
    private IUser user;
    private boolean me;

    public UserWrapper(IUser user, boolean me) {
        this.user = user;
        this.me = me;
    }

    public String getName() {
        return user.getName();
    }

    public boolean isMe() {
        return me;
    }
}
