package com.sagr.user.service;

import com.sagr.common.IResult;
import com.sagr.common.Result;
import com.sagr.common.ResultCode;
import com.sagr.user.common.IUser;
import com.sagr.user.common.IUserDao;
import com.sagr.user.common.IUserService;
import com.sagr.user.entity.User;
import com.sagr.user.security.CustomGrantedAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class UserService implements IUserService<IUser>, UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource(name = "userDao")
    private IUserDao<IUser> dao;

    public IResult<Boolean> registerNewUser(String name) {
        if (name == null || name.isEmpty()) {
            new Result<Boolean>(ResultCode.USER_NAME_IS_EMPTY);
        }
        if (isUserExists(name)) {
            new Result<Boolean>(ResultCode.USER_HAS_BEEN_REGISTERED);
        }
        User user = new User();
        user.setName(name);
        user.setSingInDate(new Date());
        HashSet<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(new CustomGrantedAuthority("ROLE_USER"));
        user.setAuthorities(grantedAuthorities);
        return dao.createUser(user);
    }

    public IResult<List<IUser>> getAllUsers() {
        return dao.getAll();
    }

    public boolean isUserExists(String name) {
        return dao.exists(name);
    }

    public void removeUser(String name) {
        dao.removeUser(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IResult<IUser> r = dao.getUser(username);
        if (r.hasError()) {
            logger.error(r.getResultCode().getDescription());
            throw new UsernameNotFoundException("User with name " + username + " not found");
        }
        return r.getResultObject();
    }
}
