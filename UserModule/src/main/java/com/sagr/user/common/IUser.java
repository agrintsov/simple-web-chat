package com.sagr.user.common;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

/**
 * Created by Sasha on 21.05.14.
 */
public interface IUser extends UserDetails {
    String getName();
    void setName(String name);

    void setAuthorities(Set<GrantedAuthority> authorities);

    Date getSingInDate();
    void setSingInDate(Date date);

}
