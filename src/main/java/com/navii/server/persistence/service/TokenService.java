package com.navii.server.persistence.service;

import com.navii.server.UserAuth;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ecrothers on 2016-07-24.
 */

public interface TokenService {

    void addAuthentication(HttpServletResponse response, UserAuth authentication);

    String getToken(UserAuth authentication);

    Authentication getAuthentication(HttpServletRequest request);
}