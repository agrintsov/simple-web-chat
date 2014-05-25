package com.sagr.user.common;

import com.sagr.common.IResult;

import java.util.List;

/**
 * Created by Sasha on 26.05.14.
 */
public interface IUserService<T extends IUser> {

    IResult<Boolean> registerNewUser(String name);

    IResult<List<T>> getAllUsers();

    boolean userExists(String name);

    void removeUser(String name);
}
