package com.sagr.user.common;

import com.sagr.common.IResult;

import java.util.List;

public interface IUserService<T extends IUser> {

    /**
     * Register new user by name
     *
     * @param name
     * @return
     */
    IResult<Boolean> registerNewUser(String name);

    /**
     * Get all users
     *
     * @return
     */
    IResult<List<T>> getAllUsers();

    /**
     * Check if exists by name
     *
     * @param name
     * @return
     */
    boolean userExists(String name);

    /**
     * Remove user by name
     *
     * @param name
     */
    void removeUser(String name);
}
