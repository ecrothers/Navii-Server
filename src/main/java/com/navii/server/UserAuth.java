package com.navii.server;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import com.navii.server.persistence.domain.Voyager;

import java.util.Collection;

/**
 * Created by ecrothers on 2016-07-24.
 */

public class UserAuth implements Authentication {

    private final Voyager voyager;
    private boolean authenticated = true;

    public UserAuth(Voyager voyager) {
        this.voyager = voyager;
    }

    @Override
    public String getName() {
        return voyager.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return voyager.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return voyager.getPassword();
    }

    @Override
    public Voyager getDetails() {
        return voyager;
    }

    @Override
    public Object getPrincipal() {
        return voyager.getUsername();
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

