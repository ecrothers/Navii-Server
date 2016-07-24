package com.navii.server;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import com.navii.server.persistence.domain.User;

import java.util.Collection;

/**
 * Created by ecrothers on 2016-07-24.
 */

public class UserAuth implements Authentication {

    private final User user;
    private boolean authenticated = true;

    public UserAuth(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public User getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}

