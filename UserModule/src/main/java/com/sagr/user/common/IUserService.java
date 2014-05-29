package com.sagr.user.common;

import com.sagr.common.IResult;

import java.util.List;

public interface IUserService<T extends IUser> {

    IResult<Boolean> registerNewUser(String name);

    IResult<List<T>> getAllUsers();

    boolean isUserExists(String name);

    void removeUser(String name);

}
