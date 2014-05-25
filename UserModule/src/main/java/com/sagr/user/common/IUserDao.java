package com.sagr.user.common;

import com.sagr.common.IResult;

import java.util.List;

/**
 * Created by Sasha on 24.05.14.
 */
public interface IUserDao<T extends IUser> {
    IResult<Boolean> saveUser(T user);
    IResult<Boolean> createUser(T user);
    IResult<T> getUser(String name);
    IResult<List<T>> getAll();
    boolean exists(String name);
    void removeUser(String name);
}
