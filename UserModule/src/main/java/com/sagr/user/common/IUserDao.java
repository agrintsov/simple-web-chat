package com.sagr.user.common;

import com.sagr.common.IResult;

import java.util.List;

public interface IUserDao<T extends IUser> {

    IResult<Boolean> createUser(T user);

    IResult<T> getUser(String name);

    IResult<List<T>> getAll();

    boolean exists(String name);

    void removeUser(String name);

}
