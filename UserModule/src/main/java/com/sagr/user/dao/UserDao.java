package com.sagr.user.dao;

import com.google.code.morphia.Key;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import com.sagr.common.IResult;
import com.sagr.common.Result;
import com.sagr.common.ResultCode;
import com.sagr.user.common.IUser;
import com.sagr.user.common.IUserDao;
import com.sagr.user.entity.User;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by Sasha on 22.05.14.
 */
public class UserDao extends BasicDAO<User, ObjectId> implements IUserDao<User> {

    public static final String NAME_FIELD = "name";

    public UserDao(Mongo mongo, Morphia morphia, String dbName) {
        super(User.class, mongo, morphia, dbName);
        removeAllUsers();
    }

    @Override
    public IResult<Boolean> saveUser(User user) {
        if (!exists(user.getName())) {
            return new Result<Boolean>(ResultCode.USER_NOT_FOUND);
        }
        save(user);
        return new Result<Boolean>(exists(user.getName()));
    }

    @Override
    public IResult<Boolean> createUser(User user) {
        if (exists(user.getName())) {
            return new Result<Boolean>(ResultCode.USER_HAS_BEEN_REGISTERED);
        }
        save(user);
        return new Result<Boolean>(exists(user.getName()));
    }

    @Override
    public IResult<User> getUser(String name) {
        Query<User> query = getDatastore().createQuery(entityClazz);
        query.field(NAME_FIELD).equal(name);
        User u = query.get();
        if (u == null) {
            new Result<User>(ResultCode.USER_NOT_FOUND);
        }
        return new Result<User>(u);
    }

    @Override
    public IResult<List<User>> getAll() {
        Query<User> query = getDatastore().createQuery(entityClazz);
        List<User> users = query.asList();
        return new Result<List<User>>(users);
    }

    @Override
    public boolean exists(String name) {
        Query<User> query = getDatastore().createQuery(entityClazz);
        query.field(NAME_FIELD).equal(name);
        return exists(query);
    }

    @Override
     public void removeUser(String name) {
        Query<User> query = getDatastore().createQuery(entityClazz);
        query.field(NAME_FIELD).equal(name);
        deleteByQuery(query);
        return;
    }

    public void removeAllUsers() {
        Query<User> query = getDatastore().createQuery(entityClazz);
        deleteByQuery(query);
        return;
    }
}
