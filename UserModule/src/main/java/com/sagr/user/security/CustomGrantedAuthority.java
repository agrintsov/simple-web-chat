package com.sagr.user.security;

import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = Long.MAX_VALUE;

    private String role;

    public CustomGrantedAuthority(String role) {
        this.role = role;
    }

    //Constructor for morphia
    public CustomGrantedAuthority() {
    }

    public String getAuthority() {
        return role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof CustomGrantedAuthority) {
            return role.equals(((CustomGrantedAuthority) obj).role);
        }

        return false;
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }

}
