package com.vignesh.weather.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails  {

    private final UsersModel principal;

    public UserPrincipal (UsersModel user) {
        this.principal = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return principal.getPassword();
    }

    @Override
    public String getUsername() {
        return principal.getUsername();
    }
}
